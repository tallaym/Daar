package com.daar.adapter.in.rest.request.auth;

public class LogoutRequest {

    private String token;

    public LogoutRequest(String refreshToken) {
        this.token = refreshToken;
    }

    public String getRefreshToken() {
        return token;
    }

    public void setRefreshToken(String refreshToken) {
        this.token = refreshToken;
    }
}
