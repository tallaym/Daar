package com.daar.core.domain.validators;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class DateValidators {

    public static Instant validateToInstant(String dateStr) {
        try {
            LocalDate localDate = LocalDate.parse(dateStr);
            return localDate.atStartOfDay(ZoneOffset.UTC).toInstant();
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Format de date invalide. Attendu: yyyy-MM-dd (ISO 8601).", e
            );
        }
    }

    public static Instant validateTimestamp(String timestampStr) {
        try {
            return Instant.parse(timestampStr);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Format d'Instant invalide. Attendu: yyyy-MM-dd'T'HH:mm:ss'Z'.", e
            );
        }
    }

    /** Vérifie que "date" est bien AVANT "now" */
    public static void validatePast(Instant date, Instant now) {
        if (!date.isBefore(now)) {
            throw new IllegalArgumentException("La date doit être dans le passé.");
        }
    }

    /** Vérifie que "date" est bien APRÈS "now" */
    public static void validateFuture(Instant date, Instant now) {
        if (!date.isAfter(now)) {
            throw new IllegalArgumentException("La date doit être dans le futur.");
        }
    }

    /** Version simple utilisée en prod */
    public static void validatePast(Instant date) {
        validatePast(date, Instant.now());
    }

    public static void validateFuture(Instant date) {
        validateFuture(date, Instant.now());
    }

}
