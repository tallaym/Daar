package com.daar.core.port.in.dto.permission;

import java.time.Instant;
import java.util.UUID;

public class CreateUseRoleDTO {

    private UUID userId;
    private UUID roleId;
    private UUID createdBy;
    private Instant expiresAt;

    public CreateUseRoleDTO(UUID userId, UUID roleId, UUID createdBy, Instant expiresAt) {
        this.userId = userId;
        this.roleId = roleId;
        this.createdBy = createdBy;
        this.expiresAt = expiresAt;
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

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }
}
