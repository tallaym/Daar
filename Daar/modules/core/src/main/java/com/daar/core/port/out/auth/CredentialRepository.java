package com.daar.core.port.out.auth;

import com.daar.core.model.auth.Credential;

import java.util.List;
import java.util.UUID;

public interface CredentialRepository {
    Credential save(Credential credential);
    List<Credential> findByUserId(UUID userId);
}
