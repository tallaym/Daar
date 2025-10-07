package com.daar.core.domain.model.document;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public class TypeDocument {
    private UUID id;
    private String name;           // ex: 'CNI', 'Facture'
    private String description;
    private UUID createdBy;
    private UUID updatedBy;
    private Instant createdAt;
    private Instant updatedAt;
    private Set<Document> ListDocument;
    private Instant SuspendedUntil;


    public TypeDocument() {
    }

    public TypeDocument(String name, String description, UUID createdBy) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
    }


    public TypeDocument(UUID id, String name, String description, UUID createdBy, UUID updatedBy, Instant createdAt, Instant updatedAt, Set<Document> listDocument, Instant suspendedUntil) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.SuspendedUntil = suspendedUntil;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Set<Document> getListDocument() {
        return ListDocument;
    }

    public void setListDocument(Set<Document> listDocument) {
        ListDocument = listDocument;
    }

    public Instant getSuspendedUntil() {
        return SuspendedUntil;
    }

    public void setSuspendedUntil(Instant suspendedUntil) {
        SuspendedUntil = suspendedUntil;
    }
}
