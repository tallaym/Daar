package com.daar.core.domain.validators;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class StringValidatorsTest {

    @Test
    void validateFirstname_shouldPassForValidName() {
        assertThatCode(() -> StringValidators.validateFirstname("Jean"))
                .doesNotThrowAnyException();
    }

    @Test
    void validateFirstname_shouldFailForInvalidName() {
        assertThatThrownBy(() -> StringValidators.validateFirstname("J3"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Lettres et espaces");
    }

    @Test
    void validateLastname_shouldPassForValidName() {
        assertThatCode(() -> StringValidators.validateLastname("O'Neil"))
                .doesNotThrowAnyException();
    }

    @Test
    void validateLastname_shouldFailForInvalidName() {
        assertThatThrownBy(() -> StringValidators.validateLastname("123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("apostrophes");
    }

    @Test
    void validateEmail_shouldPassForValidEmail() {
        assertThatCode(() -> StringValidators.validateEmail("test@example.com"))
                .doesNotThrowAnyException();
    }

    @Test
    void validateEmail_shouldFailForInvalidEmail() {
        assertThatThrownBy(() -> StringValidators.validateEmail("invalid-email"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("email");
    }

    @Test
    void validatePhone_shouldPassForValidPhone() {
        assertThatCode(() -> StringValidators.validatePhone("+221770123456"))
                .doesNotThrowAnyException();
    }

    @Test
    void validatePhone_shouldFailForInvalidPhone() {
        assertThatThrownBy(() -> StringValidators.validatePhone("abcdef"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("téléphone");
    }

    @Test
    void validateOrigin_shouldPassForValidCountry() {
        assertThatCode(() -> StringValidators.validateOrigin("SN"))
                .doesNotThrowAnyException();
    }

    @Test
    void validateOrigin_shouldFailForInvalidCountry() {
        assertThatThrownBy(() -> StringValidators.validateOrigin("XX"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("pays");
    }

    @Test
    void validateCNI_shouldPassForValidCNI() {
        assertThatCode(() -> StringValidators.validateCNI("1234567890326"))
                .doesNotThrowAnyException();
    }

    @Test
    void validateCNI_shouldFailForInvalidCNI() {
        assertThatThrownBy(() -> StringValidators.validateCNI("3214569873654"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("CNI");
    }

    @Test
    void validatePassport_shouldPassForValidPassport() {
        assertThatCode(() -> StringValidators.validatePassport("A12345678"))
                .doesNotThrowAnyException();
    }

    @Test
    void validatePassport_shouldFailForInvalidPassport() {
        assertThatThrownBy(() -> StringValidators.validatePassport("1234"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("passeport");
    }

}
