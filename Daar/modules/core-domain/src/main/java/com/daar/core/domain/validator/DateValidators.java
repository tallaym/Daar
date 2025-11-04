package com.daar.core.domain.validator;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class DateValidators {

    public static Instant validateToInstant(String dateStr) {
        try {
            LocalDate localDate = LocalDate.parse(dateStr); // Parsing ISO date
            return localDate.atStartOfDay(ZoneOffset.UTC).toInstant();
        } catch (Exception e) {
            throw new IllegalArgumentException("Format de date invalide. Attendu: yyyy-MM-dd (ISO 8601).", e);
        }
    }

    public static Instant validateTimestamp(String timestampStr) {
        try {
            return Instant.parse(timestampStr);
        } catch (Exception e) {
            throw new IllegalArgumentException("Format d'Instant invalide. Attendu: yyyy-MM-dd'T'HH:mm:ss'Z'.", e);
        }
    }

    public static void validatePast(Instant date) {
        Instant now = Instant.now();
        if (date.isAfter(now)) {
            throw new IllegalArgumentException("La date ne peut pas être dans le futur.");
        }
    }

    public static void validateFuture(Instant date) {
        Instant now = Instant.now();
        if (date.isBefore(now)) {
            throw new IllegalArgumentException("La date ne peut pas être dans le passé.");
        }
    }

    public static void validatePeriod(Instant startDate, Instant endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin.");
        }
    }




}
