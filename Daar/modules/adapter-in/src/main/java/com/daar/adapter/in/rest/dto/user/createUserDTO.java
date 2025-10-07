package com.daar.adapter.in.rest.dto.user;

import java.util.UUID;

public class createUserDTO {

    String firstname, lastname, phone;
    UUID createdBy;

    public createUserDTO() {
    }

    public createUserDTO(String firstname, String lastname, String phone, UUID createdBy) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.createdBy = createdBy;
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
