package com.daar.adapter.in.rest.response;

import com.daar.core.domain.model.auth.User;

import java.time.Instant;
import java.util.UUID;

public class CreateUserResponse {

    private UUID id;
    private String keyCloakId;
    private Instant createdAt;

    public CreateUserResponse(UUID id, String keyCloakId, Instant createdAt) {
        this.id = id;
        this.keyCloakId = keyCloakId;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getKeyCloakId() {
        return keyCloakId;
    }

    public void setKeyCloakId(String keyCloakId) {
        this.keyCloakId = keyCloakId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
