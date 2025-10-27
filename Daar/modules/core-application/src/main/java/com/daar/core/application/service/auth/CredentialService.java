package com.daar.core.application.service.auth;


import com.daar.core.domain.model.auth.Credential;
import com.daar.core.port.in.dto.credential.CreateCredentialCommand;
import com.daar.core.port.in.dto.credential.CredentialDTO;
import com.daar.core.port.in.dto.credential.GetCredentialQuery;
import com.daar.core.port.in.dto.credential.UpdateCredentialCommand;
import com.daar.core.port.in.usecase.auth.CredentialUseCase;
import com.daar.core.port.out.auth.CredentialRepository;

import java.util.List;
import java.util.UUID;

public class CredentialService implements CredentialUseCase {

    private final CredentialRepository cr;

    public CredentialService(CredentialRepository cr) {
        this.cr = cr;
    }

    @Override
    public CredentialDTO create(CreateCredentialCommand dto) {
        // Création de l'entité sans ID (généré par le repository)
        Credential cred = new Credential(
                dto.getUserId(),
                dto.getType(),
                dto.getIdentifier(),
                dto.getSecret(),
                dto.getExpiresAt()
        );

        // Persistance
        Credential saved = cr.insert(cred);

        // Retour en DTO métier
        return mapToDTO(saved);
    }

    @Override
    public CredentialDTO modify(String identifier, UpdateCredentialCommand dto) {
        Credential cred = cr.findByIdentifier(identifier);
        if (cred == null) {
            throw new RuntimeException("Credential not found");
        }

        cred.setSecret(dto.getSecret());
        cred.setExpiresAt(dto.getExpiresAt());
        cred.setUpdatedAt(dto.getUpdatedAt());

        cr.update(cred);

        return mapToDTO(cred);
    }


    @Override
    public List<CredentialDTO> userCredentials(GetCredentialQuery query) {
        // Conversion des entités en DTO
        return cr.findByUserId(query.getUserId())
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
    private CredentialDTO mapToDTO(Credential cred) {
        return new CredentialDTO(
                cred.getId(),
                cred.getUserId(),
                cred.getType(),
                cred.getIdentifier(),
                cred.getSecret(),
                cred.getExpiresAt()
        );
    }
}
