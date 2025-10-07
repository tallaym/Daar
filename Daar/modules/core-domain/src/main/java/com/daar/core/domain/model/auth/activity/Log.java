package com.daar.core.domain.model.auth.activity;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class Log {
    private UUID id;
    private String action;       // ex: login_attempt
    private String resource;     // ex: invoice, user, session
    private UUID resourceId;
    private boolean success;
    private Map<String, Object> details; // JSONB → map clé/valeur
    private UUID userId;
    private Instant createdAt;

    public Log(String action, String resource, UUID resourceId, boolean success, Map<String, Object> details, UUID userId) {
        this.id = UUID.randomUUID();
        this.action = action;
        this.resource = resource;
        this.resourceId = resourceId;
        this.success = success;
        this.details = details;
        this.userId = userId;
        this.createdAt = Instant.now();
    }

    public Log(UUID id, String action, String resource, UUID resourceId, boolean success, Map<String, Object> details, UUID userId, Instant createdAt) {
        this.id = id;
        this.action = action;
        this.resource = resource;
        this.resourceId = resourceId;
        this.success = success;
        this.details = details;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public UUID getResourceId() {
        return resourceId;
    }

    public void setResourceId(UUID resourceId) {
        this.resourceId = resourceId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public void setDetails(Map<String, Object> details) {
        this.details = details;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
