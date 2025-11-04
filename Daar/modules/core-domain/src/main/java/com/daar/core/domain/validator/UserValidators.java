package com.daar.core.domain.validator;

import com.daar.core.domain.model.auth.User;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class UserValidators {

    public static void validateUUID(String uuidStr) {
        try {
            UUID.fromString(uuidStr); // va lancer une exception si invalide
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Format d'id invalide: " + uuidStr);
        }
    }


    public static void newUserValidator(String firstname, String lastname, String phone) {
        StringValidators.validateFirstname(firstname);
        StringValidators.validateLastname(lastname);
        StringValidators.validatePhone(phone);
    }

    public static void newUserValidator(String firstname, String lastname, String phone, UUID creatorId) {
        StringValidators.validateFirstname(firstname);
        StringValidators.validateLastname(lastname);
        StringValidators.validatePhone(phone);
        validateUUID(creatorId.toString());
    }

    public static void updateUserValidator(String firstname, String lastname, String origin, String identityType, String identityNumber, String address, String email, String phone, Instant updatedAt, Instant suspendedUntil, UUID updatedBy, UUID suspendedBy) {
        StringValidators.validateFirstname(firstname);
        StringValidators.validateLastname(lastname);
        StringValidators.validatePhone(phone);

        if(origin != null) StringValidators.validateOrigin(origin);
        if(email != null) StringValidators.validateEmail(email);
        if(identityType != null && identityNumber != null) validateIdentity(identityType, identityNumber);
        if(suspendedUntil != null) DateValidators.validateFuture(suspendedUntil);
        if(updatedBy != null) validateUUID(updatedBy.toString());
        if(suspendedBy != null) validateUUID(suspendedBy.toString());
    }

    public static void getUserValidator(UUID userId) {
        validateUUID(userId.toString());
    }


    public static void usersListValidator(String dateStr) {
        DateValidators.validateToInstant(dateStr);
    }
    public static void usersListValidator(Instant startDate, Instant endDate) {
        DateValidators.validatePeriod(startDate, endDate);
    }

    public static void validateIdentity(String identityType, String identityNumber) {
        User.IdentityType type;
        try {
            type = User.IdentityType.valueOf(identityType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Type d'identité invalide: " + identityType);
        }

        switch (type) {
            case PASSEPORT:
                StringValidators.validatePassport(identityNumber);
                break;
            case CNI:
                StringValidators.validateCNI(identityNumber);
                break;
            case PERMIS:
                StringValidators.validateDriverLicense(identityNumber);
                break;
            case SEJOUR:
                StringValidators.validateSejour(identityNumber);
                break;
            default:
                throw new IllegalArgumentException("Type d'identité non pris en charge: " + identityType);
        }
    }


}
