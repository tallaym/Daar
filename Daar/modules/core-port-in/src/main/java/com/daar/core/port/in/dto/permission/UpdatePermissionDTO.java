package com.daar.core.port.in.dto.permission;

import java.time.Instant;
import java.util.UUID;

public class UpdatePermissionDTO {


    private UUID id;
    private String permission_name;
    private String description;
    private Instant updatedAt;
    private UUID updatedBy;
    private UUID roleId;

    public UpdatePermissionDTO(UUID id, String permission_name, String description, Instant updatedAt, UUID updatedBy, UUID roleId) {
        this.id = id;
        this.permission_name = permission_name;
        this.description = description;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.roleId = roleId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPermission_name() {
        return permission_name;
    }

    public void setPermission_name(String permission_name) {
        this.permission_name = permission_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UUID getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UUID updatedBy) {
        this.updatedBy = updatedBy;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }
}
