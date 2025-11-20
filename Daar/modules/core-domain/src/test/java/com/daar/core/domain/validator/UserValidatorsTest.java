package com.daar.core.domain.validator;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserValidatorsTest {

    @Test
    void validateUUID_shouldNotThrowForValidUUID() {
        UUID valid = UUID.randomUUID();
        assertThatCode(() -> UserValidators.validateUUID(valid.toString()))
                .doesNotThrowAnyException();
    }

    @Test
    void validateUUID_shouldThrowForInvalidUUID() {
        String invalid = "123-invalid-uuid";
        assertThatThrownBy(() -> UserValidators.validateUUID(invalid))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Format d'id invalide");
    }

    @Test
    void updateUserValidator_shouldFailForPastSuspendedUntil() {
        Instant past = Instant.now().minusSeconds(10);
        assertThatThrownBy(() -> UserValidators.updateUserValidator(
                "John", "Doe", null, null, null, null, null, null, past, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("future");
    }


    @Test
    void validateIdentity_shouldThrowOnInvalidType() {
        assertThatThrownBy(() -> UserValidators.validateIdentity("INVALID", "123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Type d'identité invalide");
    }



}
