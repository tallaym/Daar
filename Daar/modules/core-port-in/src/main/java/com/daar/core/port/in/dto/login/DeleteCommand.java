package com.daar.core.port.in.dto.login;

public class DeleteCommand {

    private String keyCloakId;

    public DeleteCommand(String keyCloakId) {
        this.keyCloakId = keyCloakId;
    }

    public String getKeyCloakId() {
        return keyCloakId;
    }

    public void setKeyCloakId(String keyCloakId) {
        this.keyCloakId = keyCloakId;
    }
}
