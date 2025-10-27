package com.daar.adapter.in.rest.config;

import com.daar.core.port.out.auth.JwtRepository;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JwtFilter {

    private final JwtRepository jwtRepo;

    public JwtFilter(JwtRepository jwtProvider) {
        this.jwtRepo = jwtProvider;
    }

    /**
     * Vérifie la requête et attache les claims à l'HttpExchange.
     * @param exchange la requête HTTP
     * @return true si autorisé, false sinon
     */
    public boolean authorize(HttpExchange exchange) throws IOException {
        String authHeader = exchange.getRequestHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendUnauthorized(exchange, "Missing Authorization header");
            return false;
        }

        String token = authHeader.substring(7);

        if (!jwtRepo.validateToken(token)) {
            sendUnauthorized(exchange, "Invalid or expired token");
            return false;
        }

        // Attacher les claims principaux
        UUID userId = jwtRepo.extractUserId(token);
        UUID userRoleId = jwtRepo.extractUseRoleId(token);

        exchange.setAttribute("userId", userId);
        exchange.setAttribute("userRoleId", userRoleId);

        // Si tu veux récupérer tous les claims pour usage futur
        Map<String, Object> claims = jwtRepo.extractAllClaims(token);
        exchange.setAttribute("claims", claims);

        return true;
    }

    private void sendUnauthorized(HttpExchange exchange, String message) throws IOException {
        String response = "{\"error\":\"" + message + "\"}";
        exchange.sendResponseHeaders(401, response.getBytes().length);
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }

    public boolean authorizeRole(HttpExchange exchange, String requiredRole) throws IOException {
        if (!authorize(exchange)) return false;

        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) exchange.getAttribute("roles");
        if (roles == null || !roles.contains(requiredRole)) {
            sendUnauthorized(exchange, "Insufficient permissions");
            return false;
        }

        return true;
    }

    public Object getClaim(HttpExchange exchange, String claimName) {
        @SuppressWarnings("unchecked")
        Map<String, Object> claims = (Map<String, Object>) exchange.getAttribute("claims");
        return claims != null ? claims.get(claimName) : null;
    }




}
