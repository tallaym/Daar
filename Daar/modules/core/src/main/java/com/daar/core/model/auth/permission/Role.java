package com.daar.core.model.auth.permission;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public class Role {

    private UUID id;
    private String rolename;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
    private UUID createdBy;
    private UUID updatedBy;


    public Role(String rolename, String description, UUID createdBy) {
        this.id = UUID.randomUUID();
        this.rolename = rolename;
        this.description = description;
        this.createdAt = Instant.now();
        this.createdBy = createdBy;
    }

    public Role(UUID id, String rolename, String description, Instant createdAt, Instant updatedAt, Instant deletedAt, UUID createdBy, UUID updatedBy) {
        this.id = id;
        this.rolename = rolename;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String role_name) {
        this.rolename = role_name;
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

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
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
