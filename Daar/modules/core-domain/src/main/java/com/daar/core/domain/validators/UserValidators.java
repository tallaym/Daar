package com.daar.core.domain.validators;

import com.daar.core.domain.model.auth.User;

import java.time.Instant;
import java.util.SimpleTimeZone;
import java.util.UUID;

public class UserValidators {

    public static void validateUUID(String uuidStr) {
        try {
            UUID.fromString(uuidStr); // va lancer une exception si invalide
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Format d'id invalide: " + uuidStr);
        }
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

    public static void newUserValidator(
            String firstname,
            String lastname,
            String phone
    ) {
        StringValidators.validateFirstname(firstname);
        StringValidators.validateLastname(lastname);
        StringValidators.validatePhone(phone);
    }

    public static void updateUserValidator(
            String firstname,
            String lastname,
            String origin,
            User.IdentityType identityType,
            String identityNumber,
            String email,
            String phone,
            Instant suspendedUntil,
            UUID updatedBy,
            UUID suspendedBy
    ) {
        if (firstname != null) {
            StringValidators.validateFirstname(firstname);
        }
        if (lastname != null) {
            StringValidators.validateLastname(lastname);
        }
        if (origin != null) {
            StringValidators.validateOrigin(origin);
        }
        if (identityType != null && identityNumber != null) {
            validateIdentity(String.valueOf(identityType), identityNumber);
        }
        if (email != null) {
            StringValidators.validateEmail(email);
        }
        if (phone != null) {
            StringValidators.validatePhone(phone);
        }
        if (suspendedUntil != null) {
            DateValidators.validateFuture(suspendedUntil);
        }
        if (updatedBy != null) {
            validateUUID(String.valueOf(updatedBy));
        }
        if (suspendedBy != null) {
            validateUUID(String.valueOf(suspendedBy));
        }
    }

}
