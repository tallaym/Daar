package com.daar.core.port.out.auth;


import com.daar.core.domain.model.auth.Credential;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface CredentialRepository {
    Credential insert(UUID userId, Credential.CredentialType type, String identifier, String secret, Instant expiresAt);

    Credential update(Credential cr);
    List<Credential> findByUserId(UUID userId);

}
