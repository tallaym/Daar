package com.daar.core.usecase.auth;

import com.daar.core.usecase.auth.query.KeycloakData;
import com.daar.core.domain.repository.KeycloakTokenDTO;

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
