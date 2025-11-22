package com.daar.core.domain.repository;

public record KeycloakTokenDTO(
        String accessToken,
        String refreshToken,
        Long accessTokenExpiry,
        Long refreshTokenExpiry
) {

}
