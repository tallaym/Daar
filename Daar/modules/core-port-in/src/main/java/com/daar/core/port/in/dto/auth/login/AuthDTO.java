package com.daar.core.port.in.dto.auth.login;

import java.time.Instant;

public class AuthDTO<T> {
    private boolean success;
    private String message;
    private T data;
    private Instant accessTokenExpiry;
    private Instant refreshTokenExpiry;

    public AuthDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public AuthDTO(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public AuthDTO(boolean success, String message, T data, Instant accessTokenExpiry, Instant refreshTokenExpiry) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.accessTokenExpiry = accessTokenExpiry;
        this.refreshTokenExpiry = refreshTokenExpiry;
    }



    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        data = data;
    }

    public Instant getAccessTokenExpiry() {
        return accessTokenExpiry;
    }

    public void setAccessTokenExpiry(Instant accessTokenExpiry) {
        this.accessTokenExpiry = accessTokenExpiry;
    }

    public Instant getRefreshTokenExpiry() {
        return refreshTokenExpiry;
    }

    public void setRefreshTokenExpiry(Instant refreshTokenExpiry) {
        this.refreshTokenExpiry = refreshTokenExpiry;
    }
}