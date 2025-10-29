package com.daar.adapter.in.rest.response;

import java.time.Instant;
import java.util.UUID;

public class UpdateUserResponse {

    private UUID id;
    private String firstname;
    private String lastname;
    private String origin;
    private String identityType;
    private String identityNumber;
    private String address;
    private String email;
    private String phone;


    private Instant updatedAt;
    private Instant suspendedUntil;

    private UUID updatedBy;
    private UUID suspendedBy;

    public UpdateUserResponse(UUID id, String firstname, String lastname, String origin, String identityType, String identityNumber, String address, String email, String phone, Instant updatedAt, Instant suspendedUntil, UUID updatedBy, UUID suspendedBy) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.origin = origin;
        this.identityType = identityType;
        this.identityNumber = identityNumber;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.updatedAt = updatedAt;
        this.suspendedUntil = suspendedUntil;
        this.updatedBy = updatedBy;
        this.suspendedBy = suspendedBy;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public UUID getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UUID updatedBy) {
        this.updatedBy = updatedBy;
    }

    public UUID getSuspendedBy() {
        return suspendedBy;
    }

    public void setSuspendedBy(UUID suspendedBy) {
        this.suspendedBy = suspendedBy;
    }
}
