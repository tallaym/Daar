package com.daar.core.port.in.dto.permission;

import java.util.UUID;

public class CreatePermissionDTO {

    String permission_name, description;
    UUID createdBy;

    public CreatePermissionDTO(String roleName, String description, UUID createdBy) {
        this.permission_name = roleName;
        this.description = description;
        this.createdBy = createdBy;
    }

    public String getPermission_name() {
        return permission_name;
    }

    public void setPermission_name(String roleName) {
        this.permission_name = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }
}
