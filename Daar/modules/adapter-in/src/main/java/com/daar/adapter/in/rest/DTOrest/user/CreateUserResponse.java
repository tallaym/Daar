package com.daar.adapter.in.rest.DTOrest.user;

import com.daar.core.domain.model.auth.User;

import java.time.Instant;
import java.util.UUID;

public class CreateUserResponse {

    private UUID id;
    private Instant createdAt;

    public CreateUserResponse(UUID id, Instant createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
