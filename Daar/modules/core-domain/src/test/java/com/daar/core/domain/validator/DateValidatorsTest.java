package com.daar.core.domain.validator;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class DateValidatorsTest {

    // --- validateToInstant ---
    @Test
    void validateToInstant_shouldReturnInstant_whenValidDate() {
        Instant result = DateValidators.validateToInstant("2025-11-20");
        assertNotNull(result);
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
        Instant result = DateValidators.validateTimestamp("2025-11-20T12:00:00Z");
        assertNotNull(result);
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
        Instant future = Instant.now().plusSeconds(3600);
        assertThrows(IllegalArgumentException.class, () -> DateValidators.validatePast(future));
    }

    @Test
    void validatePast_shouldPass_whenPastDate() {
        Instant past = Instant.now().minusSeconds(3600);
        assertDoesNotThrow(() -> DateValidators.validatePast(past));
    }

    // --- validateFuture ---
    @Test
    void validateFuture_shouldThrow_whenPastDate() {
        Instant past = Instant.now().minusSeconds(3600);
        assertThrows(IllegalArgumentException.class, () -> DateValidators.validateFuture(past));
    }

    @Test
    void validateFuture_shouldPass_whenFutureDate() {
        Instant future = Instant.now().plusSeconds(3600);
        assertDoesNotThrow(() -> DateValidators.validateFuture(future));
    }

    // --- validatePeriod ---
    @Test
    void validatePeriod_shouldThrow_whenStartAfterEnd() {
        Instant start = Instant.now().plusSeconds(3600);
        Instant end = Instant.now();
        assertThrows(IllegalArgumentException.class, () -> DateValidators.validatePeriod(start, end));
    }

    @Test
    void validatePeriod_shouldPass_whenStartBeforeEnd() {
        Instant start = Instant.now();
        Instant end = Instant.now().plusSeconds(3600);
        assertDoesNotThrow(() -> DateValidators.validatePeriod(start, end));
    }
}
