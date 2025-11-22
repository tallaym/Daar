package com.daar.core.usecase.auth.dto;

import java.time.Instant;

public record AuthDTO<T>(
        boolean success,
        String message,
        T data,
        Instant accessTokenExpiry,
        Instant refreshTokenExpiry
) {
    public AuthDTO(boolean success, String message) {
        this(success, message, null, null, null);
    }

    public AuthDTO(boolean success, String message, T data) {
        this(success, message, data, null, null);
    }
}
