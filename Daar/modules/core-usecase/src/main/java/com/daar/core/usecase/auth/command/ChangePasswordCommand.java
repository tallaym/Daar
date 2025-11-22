package com.daar.core.usecase.auth.command;

public record ChangePasswordCommand (

     String keycloakId,
     String newPassword) {
}
