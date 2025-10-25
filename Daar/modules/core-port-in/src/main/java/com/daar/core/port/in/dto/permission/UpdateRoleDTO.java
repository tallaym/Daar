package com.daar.core.port.in.dto.permission;

import java.time.Instant;
import java.util.UUID;

public class UpdateRoleDTO {

    UUID id, updatedBy;
    String roleName, description;
    Instant updatedAt, suspendedUntil;

    public UpdateRoleDTO(UUID id, UUID updatedBy, String roleName, String description, Instant updatedAt, Instant suspendedUntil) {
        this.id = id;
        this.updatedBy = updatedBy;
        this.roleName = roleName;
        this.description = description;
        this.updatedAt = updatedAt;
        this.suspendedUntil = suspendedUntil;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }



    public UUID getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UUID updatedBy) {
        this.updatedBy = updatedBy;
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



    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getSuspendedUntil() {
        return suspendedUntil;
    }

    public void setSuspendedUntil(Instant suspendedUntil) {
        this.suspendedUntil = suspendedUntil;
    }
}
