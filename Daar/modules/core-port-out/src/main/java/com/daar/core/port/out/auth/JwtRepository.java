package com.daar.core.port.out.auth;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public interface JwtRepository {

    enum TokenType { ACCESS, REFRESH }

    String generateToken(UUID userId, UUID useRoleId, TokenType type);

    UUID extractUserId(String token);
    UUID extractUseRoleId(String token);
    Instant extractExpiration(String token);
    boolean isTokenExpired(String token);
    boolean validateToken(String token);

    Map<String, Object> extractAllClaims(String token);

}
