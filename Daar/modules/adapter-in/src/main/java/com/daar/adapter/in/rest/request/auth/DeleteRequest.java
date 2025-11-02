package com.daar.adapter.in.rest.request.auth;

public class DeleteRequest {

    private String KeyCloakId;

    public DeleteRequest(String email) {
        this.KeyCloakId = email;
    }

    public String getKeyCloakId() {
        return KeyCloakId;
    }

    public void setKeyCloakId(String email) {
        this.KeyCloakId = email;
    }
}
