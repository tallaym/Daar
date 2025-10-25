package com.daar.core.application.service.auth;


import com.daar.core.domain.model.auth.Credential;
import com.daar.core.port.in.dto.credential.CreateCredentialDTO;
import com.daar.core.port.in.dto.credential.UpdateCredentialDTO;
import com.daar.core.port.in.usecase.auth.CredentialUseCase;
import com.daar.core.port.out.auth.CredentialRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class CredentialService implements CredentialUseCase {

    private final CredentialRepository cr;

    public CredentialService(CredentialRepository cr) {
        this.cr = cr;
    }

    @Override
    public UUID create(CreateCredentialDTO dto) {
        return cr.insert(dto.getUserId(),dto.getType(), dto.getIdentifier(), dto.getSecret(),dto.getExpiresAt()).getId();
    }

    @Override
    public Credential modify(String identifier, UpdateCredentialDTO dto) {
        Credential cred = cr.findByIdentifier(identifier);

        cred.setUpdatedAt(dto.getUpdatedAt());
        cred.setExpiresAt(dto.getExpiresAt());
        cred.setSecret(dto.getSecret());


        return cr.update(cred);
    }

    @Override
    public List<Credential> userCredentials(UUID id) {
        return cr.findByUserId(id);
    }
}
