package com.daar.adapter.out.jdbc;

public record KeyCloakConfig(
        String serverUrl,
        String realm,
        String clientId,
        String clientSecret,
        String adminUsername,
        String adminPassword
) {
}
