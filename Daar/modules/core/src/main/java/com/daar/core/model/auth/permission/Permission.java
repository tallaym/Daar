package com.daar.core.model.auth.permission;

import java.time.Instant;
import java.util.UUID;

public class Permission {


        private UUID id;
        private String permission_name;
        private String description;
        private Instant createdAt;
        private Instant updatedAt;
        private Instant deletedAt;
        private UUID createdBy;
        private UUID updatedBy;

        public Permission(String rolename, String description, UUID createdBy) {
            this.id = UUID.randomUUID();
            this.permission_name = rolename;
            this.description = description;
            this.createdAt = Instant.now();
            this.createdBy = createdBy;
        }

        public Permission(UUID id, String rolename, String description, Instant createdAt, Instant updatedAt, Instant deletedAt, UUID createdBy, UUID updatedBy) {
            this.id = id;
            this.permission_name = rolename;
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

    public String getPermission_name() {
        return permission_name;
    }

    public void setPermission_name(String permission_name) {
        this.permission_name = permission_name;
    }
}


