package com.daar.core.domain.model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private UUID id;
    private String keycloakId;
    private String firstname;
    private String lastname;
    private String origin;
    private IdentityType identityType;
    private String identityNumber;
    private String address;
    private String email;
    private String phone;


    private Instant createdAt;
    private Instant updatedAt;
    private Instant suspendedUntil;

    private UUID createdBy;
    private UUID updatedBy;
    private UUID suspendedBy;


    public enum IdentityType {
        CNI, PASSEPORT, PERMIS, SEJOUR
    }

    public User(String firstname, String lastname, String phone, String keycloakId, UUID createdBy) {
        this.id = UUID.randomUUID();
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.keycloakId = keycloakId;
        this.createdBy = createdBy;
    }



}