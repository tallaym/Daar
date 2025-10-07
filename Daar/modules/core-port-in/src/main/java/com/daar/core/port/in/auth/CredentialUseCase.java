package com.daar.core.port.in.auth;


import com.daar.core.domain.model.auth.Credential;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface CredentialUseCase {

    UUID create(UUID userId, Credential.CredentialType type, String identifier, String secret, Instant expiresAt);
    Credential modify(Credential mdp);

    List<Credential> userCredentials(UUID id);

}
