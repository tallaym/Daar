package com.daar.core.model.auth;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public class User {

    private UUID id;
    private String firstname;
    private String lastname;
    private String origin = "Senegalese";
    private IdentityType identityType;
    private String identityNumber;
    private String adress;
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

    //constructeur vide
    public User(){}

    //constructeur basique
    public User(String firstname, String lastname, String phone) {
        this.id = UUID.randomUUID();
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.createdAt = Instant.now();

    }

    //constructeur complet
    public User(UUID id, String firstname, String lastname, String origin, IdentityType identityType, String identityNumber, String adress, String email, String phone, Instant createdAt, Instant updatedAt, Instant suspendedUntil, UUID createdBy, UUID updatedBy, UUID suspendedBy) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.origin = origin;
        this.identityType = identityType;
        this.identityNumber = identityNumber;
        this.adress = adress;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.suspendedUntil = suspendedUntil;
        this.createdBy = createdBy;
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

        public IdentityType getIdentityType() {
            return identityType;
        }

        public void setIdentityType(IdentityType identityType) {
            this.identityType = identityType;
        }

        public String getIdentityNumber() {
            return identityNumber;
        }

        public void setIdentityNumber(String identityNumber) {
            this.identityNumber = identityNumber;
        }

        public String getAdress() {
            return adress;
        }

        public void setAdress(String adress) {
            this.adress = adress;
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

        public UUID getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(UUID createdBy) {
            this.createdBy = createdBy;
        }


}
