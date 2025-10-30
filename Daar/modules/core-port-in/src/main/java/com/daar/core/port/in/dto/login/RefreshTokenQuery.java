package com.daar.core.port.in.dto.login;

public class RefreshTokenQuery {

    private String refreshToken;

    public RefreshTokenQuery(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
