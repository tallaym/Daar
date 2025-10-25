package com.daar.core.domain.model.auth.activity;

import java.time.Instant;
import java.util.UUID;

public class RefreshToken {

    private UUID userId;
    private String token;
    private Instant expiresAt;
    private boolean revoked;

    public RefreshToken(UUID userId, String token, Instant expiresAt, boolean revoked) {
        this.userId = userId;
        this.token = token;
        this.expiresAt = expiresAt;
        this.revoked = revoked;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }
}
