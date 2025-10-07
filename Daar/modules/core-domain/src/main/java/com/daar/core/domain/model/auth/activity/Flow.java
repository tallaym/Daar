package com.daar.core.domain.model.auth.activity;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class Flow {
    private UUID id;
    private UUID userId;
    private String flowType;     // login_password, otp_verification...
    private String status;       // started, completed, failed...
    private String currentStep;  // otp_sent, otp_verified...
    private Instant startedAt;
    private Instant updatedAt;
    private Instant expiresAt;
    private Map<String, Object> metadata; // JSONB

    public Flow(UUID userId, String flowType, String status, String currentStep, Instant expiresAt, Map<String, Object> metadata) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.flowType = flowType;
        this.status = status;
        this.currentStep = currentStep;
        this.startedAt = Instant.now();
        this.expiresAt = expiresAt;
        this.metadata = metadata;
    }

    public Flow(UUID id, UUID userId, String flowType, String status, String currentStep, Instant startedAt, Instant updatedAt, Instant expiresAt, Map<String, Object> metadata) {
        this.id = id;
        this.userId = userId;
        this.flowType = flowType;
        this.status = status;
        this.currentStep = currentStep;
        this.startedAt = startedAt;
        this.updatedAt = updatedAt;
        this.expiresAt = expiresAt;
        this.metadata = metadata;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(String currentStep) {
        this.currentStep = currentStep;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}
