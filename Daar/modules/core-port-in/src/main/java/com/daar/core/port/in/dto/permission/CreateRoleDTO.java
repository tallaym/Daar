package com.daar.core.port.in.dto.permission;

import java.util.UUID;

public class CreateRoleDTO {

    String roleName, description;
    UUID createdBy;

    public CreateRoleDTO(String roleName, String description, UUID createdBy) {
        this.roleName = roleName;
        this.description = description;
        this.createdBy = createdBy;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
