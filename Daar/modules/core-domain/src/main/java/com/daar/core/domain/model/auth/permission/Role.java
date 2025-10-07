package com.daar.core.domain.model.auth.permission;

import java.time.Instant;
import java.util.UUID;

public class Role {

    private UUID id;
    private String roleName;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private Instant SuspendedUntil;


    public Role() {
    }

    public Role(String rolename, String description, UUID createdBy) {
        this.id = UUID.randomUUID();
        this.roleName = rolename;
        this.description = description;
        this.createdBy = createdBy;
    }

    public Role(UUID id, String roleName, String description, Instant createdAt, Instant updatedAt, UUID createdBy, UUID updatedBy, Instant suspendedUntil) {
        this.id = id;
        this.roleName = roleName;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.SuspendedUntil = suspendedUntil;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String role_name) {
        this.roleName = role_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public UUID getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UUID updatedBy) {
        this.updatedBy = updatedBy;
    }


}
