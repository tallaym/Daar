package com.daar.core.application.auth;


import com.daar.core.domain.model.auth.Credential;
import com.daar.core.port.in.auth.CredentialUseCase;
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
    public UUID create(UUID userId, Credential.CredentialType type, String identifier, String secret, Instant expiresAt) {
        return cr.insert(userId,type,identifier,secret,expiresAt).getId();
    }

    @Override
    public Credential modify(Credential mdp) {
        return cr.update(mdp);
    }

    @Override
    public List<Credential> userCredentials(UUID id) {
        return cr.findByUserId(id);
    }
}
