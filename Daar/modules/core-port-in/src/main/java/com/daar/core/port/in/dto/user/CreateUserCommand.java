package com.daar.core.port.in.dto.user;

import java.util.UUID;

public class CreateUserCommand {

    String firstname, lastname, phone;
    String KeycloakId;
    UUID createdBy;

    public CreateUserCommand() {
    }

    public CreateUserCommand(String firstname, String lastname, String phone, String keycloakId, UUID createdBy) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        KeycloakId = keycloakId;
        this.createdBy = createdBy;
    }

    public String getKeycloakId() {
        return KeycloakId;
    }

    public void setKeycloakId(String keycloakId) {
        KeycloakId = keycloakId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }
}
