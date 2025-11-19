package com.daar.core.port.in.dto.auth.login.cmd;

public class ChangePasswordCommand {

    private String keycloakId;
    private String newPassword;

    public ChangePasswordCommand(String keycloakId, String newPassword) {
        this.keycloakId = keycloakId;
        this.newPassword = newPassword;
    }

    public String getKeycloakId() {
        return keycloakId;
    }

    public void setKeycloakId(String keycloakId) {
        this.keycloakId = keycloakId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
