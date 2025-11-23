package com.daar.core.domain.validators;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class DateValidatorsTest {

    private final Instant reference = Instant.parse("2025-01-01T12:00:00Z");
    private final Instant past = reference.minusSeconds(3600);
    private final Instant future = reference.plusSeconds(3600);

    // --- validateToInstant ---
    @Test
    void validateToInstant_shouldReturnInstant_whenValidDate() {
        Instant result = DateValidators.validateToInstant("2025-01-10");
        assertEquals(Instant.parse("2025-01-10T00:00:00Z"), result);
    }

    @Test
    void validateToInstant_shouldThrow_whenInvalidDate() {
        assertThrows(IllegalArgumentException.class, () -> {
            DateValidators.validateToInstant("20-11-2025");
        });
    }

    // --- validateTimestamp ---
    @Test
    void validateTimestamp_shouldReturnInstant_whenValidISO() {
        Instant result = DateValidators.validateTimestamp("2025-01-01T12:00:00Z");
        assertEquals(Instant.parse("2025-01-01T12:00:00Z"), result);
    }

    @Test
    void validateTimestamp_shouldThrow_whenInvalidISO() {
        assertThrows(IllegalArgumentException.class, () -> {
            DateValidators.validateTimestamp("2025-11-20 12:00");
        });
    }

    // --- validatePast ---
    @Test
    void validatePast_shouldThrow_whenFutureDate() {
        assertThrows(IllegalArgumentException.class, () -> DateValidators.validatePast(future, reference));
    }

    @Test
    void validatePast_shouldPass_whenPastDate() {
        assertDoesNotThrow(() -> DateValidators.validatePast(past, reference));
    }



    // --- validateFuture ---
    @Test
    void validateFuture_shouldThrow_whenPastDate() {
        assertThrows(IllegalArgumentException.class, () -> DateValidators.validateFuture(past, reference));
    }

    @Test
    void validateFuture_shouldPass_whenFutureDate() {
        assertDoesNotThrow(() -> DateValidators.validateFuture(future, reference));
    }


}