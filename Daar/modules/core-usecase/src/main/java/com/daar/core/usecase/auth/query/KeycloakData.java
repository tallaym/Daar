package com.daar.core.usecase.auth.query;

import java.time.Instant;

public record KeycloakData(
    String accessToken,
    String refreshToken,
    Instant accessTokenExpiry,
    Instant refreshTokenExpiry
) {
}
