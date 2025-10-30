package com.daar.core.port.in.dto.login;

public class ResetPasswordCommand {

    private String contact;
    private String newPassword;

    public ResetPasswordCommand(String contact, String newPassword) {
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
