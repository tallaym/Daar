package com.daar.core.domain.validators;

import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Pattern;

public class StringValidators {

    private static final Pattern EMAIL_REGEX = Pattern.compile( "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PHONE_REGEX = Pattern.compile("^\\+\\d{7,15}$");
    private static final Pattern NATIONAL_PHONE_REGEX = Pattern.compile("^7[05678]\\d{7}$");
    private static final Pattern LASTNAME_REGEX = Pattern.compile("^[A-Za-z']{2,}$");
    private static final Pattern FIRSTNAME_REGEX = Pattern.compile("^[A-Za-z']+(?: [A-Za-z]+)*$");
    private static final Pattern CNI_REGEX = Pattern.compile("^[12]\\d{12}$");
    private static final Pattern PASSPORT_REGEX = Pattern.compile("^[A-Z0-9]{9}$");

    private static final Pattern DRIVER_LICENSE = Pattern.compile("^$");
    private static final Pattern SEJOUR = Pattern.compile("^$");



    public static void validateFirstname(String name) {
        if (name == null || !FIRSTNAME_REGEX.matcher(name).matches()) {
            throw new IllegalArgumentException("Lettres et espaces uniquement; au moins 2 caractères.");
        }
    }

    public static void validateLastname(String name) {
        if (name == null || !LASTNAME_REGEX.matcher(name).matches()) {
            throw new IllegalArgumentException("Lettres et apostrophes uniquement; au moins 2 caractères.");
        }
    }

    public static void validateEmail(String email) {
        if (email == null || !EMAIL_REGEX.matcher(email).matches()) {
            throw new IllegalArgumentException("Format d'email invalide.");
        }
    }

    public static void validatePhone(String phone) {
        if (phone == null || !(PHONE_REGEX.matcher(phone).matches() || NATIONAL_PHONE_REGEX.matcher(phone).matches())) {
            throw new IllegalArgumentException("Format de téléphone invalide.");
        }
    }

    public static void validateCNI(String cni) {
        if (cni == null || !CNI_REGEX.matcher(cni).matches()) {
            throw new IllegalArgumentException("Format de CNI invalide.");
        }
    }

    public static void validatePassport(String passport) {
        if (passport == null || !PASSPORT_REGEX.matcher(passport).matches()) {
            throw new IllegalArgumentException("Format de passeport invalide.");
        }
    }

    public static void validateOrigin(String countryCode) {
        String upperCode = countryCode.toUpperCase();
        if(Arrays.asList(Locale.getISOCountries()).contains(upperCode)){
            return;
        } else {
            throw new IllegalArgumentException("Code pays invalide.");
        }
    }

    public static void validateDriverLicense(String license) {
        if (license == null || !DRIVER_LICENSE.matcher(license).matches()) {
            throw new IllegalArgumentException("Format de permis de conduire invalide.");
        }
    }

    public static void validateSejour(String sejour) {
        if (sejour == null || !SEJOUR.matcher(sejour).matches()) {
            throw new IllegalArgumentException("Format de titre de séjour invalide.");
        }
    }
}
