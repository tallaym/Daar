package com.daar.core.port.in.dto.credential;

import java.time.Instant;
import java.util.UUID;

public class UpdateCredentialCommand {

    UUID userId;
    String identifier;
    String secret;
    Instant updatedAt, expiresAt;

    public UpdateCredentialCommand() {
    }

    public UpdateCredentialCommand(UUID userId, String identifier, String secret, Instant updatedAt, Instant expiresAt) {
        this.userId = userId;
        this.identifier = identifier;
        this.secret = secret;
        this.updatedAt = updatedAt;
        this.expiresAt = expiresAt;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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
