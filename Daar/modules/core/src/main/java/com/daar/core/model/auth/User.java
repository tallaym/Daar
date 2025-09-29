package com.daar.core.model.auth;

import com.daar.core.model.auth.permission.Role;
import com.daar.core.model.auth.permission.UseRole;
import com.daar.core.model.auth.permission.UserPermission;
import com.daar.core.model.document.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class User {

    private UUID id;
    private String firstname;
    private String lastname;
    private String origin = "Senegalese";
    private IdentityType identityType;
    private String identityNumber;
    private String address;
    private String email;
    private String phone;
    private boolean active;

    private Instant createdAt;
    private Instant updatedAt;
    private Instant suspendedUntil;

    private UUID createdBy;
    private UUID updatedBy;
    private UUID suspendedBy;

    private List<Credential> myCredentials;
    private List<UseRole> myRoles;
    private List<UserPermission> myPermissions;
    private List<Document> myDocuments;


    public enum IdentityType {
        CNI, PASSEPORT, PERMIS, SEJOUR
    }

    //constructeur vide
    public User(){}

    //constructeur basique
    public User(String firstname, String lastname, String phone, UUID createdBy) {
        this.id = UUID.randomUUID();
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.createdBy = createdBy;

    }

    //constructeur complet
    public User(UUID id, String firstname, String lastname, String origin, IdentityType identityType, String identityNumber, String adress, String email, String phone, Instant createdAt, Instant updatedAt, Instant suspendedUntil, UUID createdBy, UUID updatedBy, UUID suspendedBy, List<Credential> myCredentials, List<UseRole> myRoles, List<UserPermission> myPermissions, List<Document> myDocuments) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.origin = origin;
        this.identityType = identityType;
        this.identityNumber = identityNumber;
        this.address = adress;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.suspendedUntil = suspendedUntil;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.suspendedBy = suspendedBy;
        this.myCredentials = myCredentials;
        this.myRoles = myRoles;
        this.myPermissions = myPermissions;
        this.myDocuments = myDocuments;
    }

    public User(UUID id, String firstname, String lastname, String origin, IdentityType identityType, String identityNumber, String adress, String email, String phone, Instant createdAt, Instant updatedAt, Instant suspendedUntil, UUID createdBy, UUID updatedBy, UUID suspendedBy, boolean active) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.origin = origin;
        this.identityType = identityType;
        this.identityNumber = identityNumber;
        this.address = adress;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.suspendedUntil = suspendedUntil;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.suspendedBy = suspendedBy;
        this.myCredentials = new ArrayList<>();
        this.myRoles = new ArrayList<>();
        this.myPermissions = new ArrayList<>();
        this.myDocuments = new ArrayList<>();
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String adress) {
            this.address = adress;
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

    public List<Credential> getMyCredentials() {
        return myCredentials;
    }

    public void setMyCredentials(List<Credential> myCredentials) {
        this.myCredentials = myCredentials;
    }

    public List<UseRole> getMyRoles() {
        return myRoles;
    }

    public void setMyRoles(List<UseRole> myRoles) {
        this.myRoles = myRoles;
    }

    public List<UserPermission> getMyPermissions() {
        return myPermissions;
    }

    public void setMyPermissions(List<UserPermission> myPermissions) {
        this.myPermissions = myPermissions;
    }

    public List<Document> getMyDocuments() {
        return myDocuments;
    }

    public void setMyDocuments(List<Document> myDocuments) {
        this.myDocuments = myDocuments;
    }
}
