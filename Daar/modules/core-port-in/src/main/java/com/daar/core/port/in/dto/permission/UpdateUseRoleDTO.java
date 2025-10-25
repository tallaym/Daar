package com.daar.core.port.in.dto.permission;

import java.time.Instant;
import java.util.UUID;

public class UpdateUseRoleDTO {

    private UUID id;
    private UUID userId;
    private UUID roleId;
    private UUID updatedBy;
    private Instant updatedAt;
    private Instant expiresAt;

    public UpdateUseRoleDTO(UUID id, UUID userId, UUID roleId, UUID updatedBy, Instant updatedAt, Instant expiresAt) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
        this.updatedBy = updatedBy;
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

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public UUID getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UUID updatedBy) {
        this.updatedBy = updatedBy;
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
