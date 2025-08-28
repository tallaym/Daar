package com.daar.core.model.auth.permission;

import java.time.Instant;
import java.util.UUID;

public class UseRole {

    private UUID id;
    private UUID userId;
    private UUID roleId;

    private UUID createdBy;
    private Instant assignedAt;
    private Instant updatedAt;
    private Instant expiresAt;


    public UseRole(UUID userId, UUID roleId, UUID createdBy, Instant expiresAt) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.roleId = roleId;
        this.createdBy = createdBy;
        this.assignedAt = Instant.now();
        this.expiresAt = expiresAt;
    }

    public UseRole(UUID id, UUID userId, UUID roleId, UUID createdBy, Instant assignedAt, Instant updatedAt, Instant expiresAt) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
        this.createdBy = createdBy;
        this.assignedAt = assignedAt;
        this.updatedAt = updatedAt;
        this.expiresAt = expiresAt;
    }

    // Getters et Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public UUID getRoleId() { return roleId; }
    public void setRoleId(UUID roleId) { this.roleId = roleId; }

    public UUID getCreatedBy() { return createdBy; }
    public void setCreatedBy(UUID createdBy) { this.createdBy = createdBy; }

    public Instant getAssignedAt() { return assignedAt; }
    public void setAssignedAt(Instant assignedAt) { this.assignedAt = assignedAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }

}

