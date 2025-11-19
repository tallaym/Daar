package com.daar.core.port.in.dto.auth.login.query;

import java.time.Instant;

public record KeycloakData(
    String accessToken,
    String refreshToken,
    Instant accessTokenExpiry,
    Instant refreshTokenExpiry
) {
}
