package com.daar.core.port.in.dto.auth.login.cmd;

public class ResetPasswordCommand {

    private String contact;

    public ResetPasswordCommand(String contact) {
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    }
