package com.daar.core.domain.port_out;

public record KeycloakTokenDTO(
        String accessToken,
        String refreshToken,
        Long accessTokenExpiry,
        Long refreshTokenExpiry
) {

}
