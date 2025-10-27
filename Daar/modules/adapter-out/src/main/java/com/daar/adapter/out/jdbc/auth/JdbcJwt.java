package com.daar.adapter.out.jdbc.auth;

import com.daar.core.port.out.auth.JwtRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class JdbcJwt implements JwtRepository {

    private final Key jwtSecret;
    private final long accessTokenExpirationMs;
    private final long refreshTokenExpirationMs;

    public JdbcJwt(String secret, long accessTokenExpirationMs, long refreshTokenExpirationMs) {
        this.jwtSecret = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessTokenExpirationMs = accessTokenExpirationMs;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
    }

    /**
     * Génère un token JWT (access ou refresh) avec claims optionnels.
     */


    /**
     * Extraction générique d'un claim.
     */


    @Override
    public String generateToken(UUID userId, UUID useRoleId, JwtRepository.TokenType type) {
        Instant now = Instant.now();
        Instant expiry = now.plusMillis(type == TokenType.ACCESS ? accessTokenExpirationMs : refreshTokenExpirationMs);

        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("useRoleId", useRoleId.toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .signWith(jwtSecret, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrait le subject (userId).
     */
    public UUID extractUserId(String token) {
        return UUID.fromString(extractClaim(token, Claims::getSubject));
    }

    /**
     * Extrait un claim roleId (si stocké dans les claims).
     */
    public UUID extractUseRoleId(String token) {
        String urId = extractClaim(token, claims -> claims.get("userRoleId", String.class));
        return urId != null ? UUID.fromString(urId) : null;
    }

    /**
     * Extrait l'expiration en Instant.
     */
    public Instant extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration).toInstant();
    }

    /**
     * Vérifie si le token est expiré.
     */
    public boolean isTokenExpired(String token) {
        return Instant.now().isAfter(extractExpiration(token));
    }

    /**
     * Vérifie la validité du token (signature + parsing).
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Extraction de tous les claims.
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    public Map<String, Object> extractAllClaims(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }



}

