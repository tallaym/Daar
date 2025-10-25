package com.daar.core.port.in.dto.login;

public class LogoutResponseDTO {

    private String message;

    public LogoutResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
