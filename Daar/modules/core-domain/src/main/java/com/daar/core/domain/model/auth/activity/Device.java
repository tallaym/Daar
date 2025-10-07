package com.daar.core.domain.model.auth.activity;

import java.time.Instant;
import java.util.UUID;

public class Device {

    private UUID id;
    private UUID userId;
    private String deviceName;
    private String deviceFingerprint;
    private Instant createdAt;
    private Instant lastUsed;
    private Instant updatedAt;

    public Device(UUID userId, String deviceName, String deviceFingerprint) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.deviceName = deviceName;
        this.deviceFingerprint = deviceFingerprint;
        this.createdAt = Instant.now();
    }

    public Device(UUID id, UUID userId, String deviceName, String deviceFingerprint, Instant createdAt, Instant lastUsed, Instant updatedAt) {
        this.id = id;
        this.userId = userId;
        this.deviceName = deviceName;
        this.deviceFingerprint = deviceFingerprint;
        this.createdAt = createdAt;
        this.lastUsed = lastUsed;
        this.updatedAt = updatedAt;
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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceFingerprint() {
        return deviceFingerprint;
    }

    public void setDeviceFingerprint(String deviceFingerprint) {
        this.deviceFingerprint = deviceFingerprint;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Instant lastUsed) {
        this.lastUsed = lastUsed;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
