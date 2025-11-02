package com.daar.adapter.in.rest.request.auth;

public class ResetPasswordRequest {

    private String contact;
    private String newPassword;

    public ResetPasswordRequest(String contact, String newPassword) {
        this.contact = contact;
        this.newPassword = newPassword;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
