package com.daar.core.application.service;

import com.daar.core.port.in.dto.auth.login.query.KeycloakData;
import com.daar.core.domain.port_out.KeycloakTokenDTO;

import java.time.Instant;

public class KeycloakTokenMapper {
    public static KeycloakData toDomain(KeycloakTokenDTO dto) {
        if (dto == null) return null;

        Instant now = Instant.now();

        return new KeycloakData(
                dto.accessToken(),
                dto.refreshToken(),
                now.plusSeconds(dto.accessTokenExpiry()),
                now.plusSeconds(dto.refreshTokenExpiry())
        );
    }
}
