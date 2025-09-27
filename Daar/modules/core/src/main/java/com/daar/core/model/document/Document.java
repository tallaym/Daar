package com.daar.core.model.document;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public class Document {
    private UUID id;
    private String docName;        // nom du fichier
    private String docFormat;      // ex: application/pdf
    private String storageType;    // 'local' ou 'cloud'
    private String storagePath;    // chemin local ou URL cloud
    private boolean isSensitive;
    private UUID parentID;
    private UUID createdBy;
    private UUID updatedBy;
    private Instant createdAt;
    private Instant updatedAt;
    private UUID typeId;

    public Document(String docName, String docFormat, String storageType, String storagePath, boolean isSensitive, UUID createdBy, UUID typeId) {
        this.docName = docName;
        this.docFormat = docFormat;
        this.storageType = storageType;
        this.storagePath = storagePath;
        this.isSensitive = isSensitive;
        this.createdBy = createdBy;
        this.typeId = typeId;
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();

    }

    public Document(UUID id, String docName, String docFormat, String storageType, String storagePath, boolean isSensitive, UUID createdBy, UUID updatedBy, Instant createdAt, Instant updatedAt, UUID typeId) {
        this.id = id;
        this.docName = docName;
        this.docFormat = docFormat;
        this.storageType = storageType;
        this.storagePath = storagePath;
        this.isSensitive = isSensitive;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.typeId = typeId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocFormat() {
        return docFormat;
    }

    public void setDocFormat(String docFormat) {
        this.docFormat = docFormat;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public boolean isSensitive() {
        return isSensitive;
    }

    public void setSensitive(boolean sensitive) {
        isSensitive = sensitive;
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

    public UUID getTypeId() {
        return typeId;
    }

    public void setTypeId(UUID typeId) {
        this.typeId = typeId;
    }

    public UUID getParentID() {
        return parentID;
    }

    public void setParentID(UUID parentID) {
        this.parentID = parentID;
    }
}
