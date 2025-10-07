package com.daar.adapter.in.rest.dto.credential;

import com.daar.core.domain.model.auth.Credential;

import java.time.Instant;
import java.util.UUID;

public class updateCredentialDTO {

    UUID id, userId;
    Credential.CredentialType type;
    String identifier, secret;
    Instant updatedAt, expiresAt;

    public updateCredentialDTO() {
    }

    public updateCredentialDTO(UUID id, UUID userId, Credential.CredentialType type, String identifier, String secret, Instant updatedAt, Instant expiresAt) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.identifier = identifier;
        this.secret = secret;
        this.updatedAt = updatedAt;
        this.expiresAt = expiresAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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


    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }
}
