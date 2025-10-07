package com.daar.adapter.in.rest.dto.login;

import java.util.UUID;

public class LoginResponseDTO {

    private UUID userId;
    private String token;

    public LoginResponseDTO(UUID userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
