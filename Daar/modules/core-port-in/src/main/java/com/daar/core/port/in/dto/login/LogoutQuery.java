package com.daar.core.port.in.dto.login;

public class LogoutQuery {

    private String token;

    public LogoutQuery(String refreshToken) {
        this.token = refreshToken;
    }

    public String getRefreshToken() {
        return token;
    }

    public void setRefreshToken(String refreshToken) {
        this.token = refreshToken;
    }
}
