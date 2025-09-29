package com.daar.core.model.auth.permission;

import java.time.Instant;
import java.util.UUID;

public class UserPermission {

    private UUID id;
    private UUID userId;
    private UUID permissionId;

    private UUID createdBy;
    private Instant assignedAt;
    private Instant updatedAt;
    private Instant expiresAt;


    public UserPermission() {
    }

    public UserPermission(UUID userId, UUID permissionId, UUID createdBy, Instant expiresAt) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.permissionId = permissionId;
        this.createdBy = createdBy;
        this.assignedAt = Instant.now();
        this.expiresAt = expiresAt;
    }

    public UserPermission(UUID id, UUID userId, UUID permissionId, UUID createdBy, Instant assignedAt, Instant updatedAt, Instant expiresAt) {
        this.id = id;
        this.userId = userId;
        this.permissionId = permissionId;
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

    public UUID getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(UUID permissionId) {
        this.permissionId = permissionId;
    }

    public UUID getCreatedBy() { return createdBy; }
    public void setCreatedBy(UUID createdBy) { this.createdBy = createdBy; }

    public Instant getAssignedAt() { return assignedAt; }
    public void setAssignedAt(Instant assignedAt) { this.assignedAt = assignedAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }

}

