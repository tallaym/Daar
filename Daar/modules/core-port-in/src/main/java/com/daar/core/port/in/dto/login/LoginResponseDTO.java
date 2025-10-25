package com.daar.core.port.in.dto.login;

import com.daar.core.domain.model.auth.permission.Role;

import java.time.Instant;
import java.util.UUID;

public class LoginResponseDTO {

    private UUID useRoleId;
    private String accessToken, refreshToken, tokenType = "bearer";


    public LoginResponseDTO(UUID roleId, String accessToken, String refreshToken, String tokenType) {
        this.useRoleId = roleId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
    }


    public UUID getRoleId() {
        return useRoleId;
    }

    public void setRoleId(UUID roleId) {
        this.useRoleId = roleId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

}
