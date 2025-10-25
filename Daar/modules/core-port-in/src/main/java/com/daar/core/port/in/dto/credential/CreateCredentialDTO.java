package com.daar.core.port.in.dto.credential;

import com.daar.core.domain.model.auth.Credential;

import java.time.Instant;
import java.util.UUID;

public class CreateCredentialDTO {
   UUID userId;
   String identifier,secret;
   Credential.CredentialType type;
   Instant expiresAt;

    public CreateCredentialDTO() {
    }

    public CreateCredentialDTO(UUID userId, Credential.CredentialType type, String identifier, String secret, Instant expiresAt) {
        this.userId = userId;
        this.type = type;
        this.identifier = identifier;
        this.secret = secret;
        this.expiresAt = expiresAt;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Credential.CredentialType getType() {
        return type;
    }

    public void setType(Credential.CredentialType type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }
}
