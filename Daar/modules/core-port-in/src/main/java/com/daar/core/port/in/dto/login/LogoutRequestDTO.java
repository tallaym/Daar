package com.daar.core.port.in.dto.login;

public class LogoutRequestDTO {

    private String refreshToken;

    public LogoutRequestDTO(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
