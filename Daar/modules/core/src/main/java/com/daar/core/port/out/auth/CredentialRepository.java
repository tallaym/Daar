package com.daar.core.port.out.auth;

import com.daar.core.model.auth.Credential;

import java.util.List;
import java.util.UUID;

public interface CredentialRepository {
    Credential insert(Credential credential);

    Credential update(Credential cr);
    List<Credential> findByUserId(UUID userId);

}
