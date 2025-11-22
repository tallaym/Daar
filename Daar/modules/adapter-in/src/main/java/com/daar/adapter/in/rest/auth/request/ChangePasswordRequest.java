package com.daar.adapter.in.rest.auth.request;

public record ChangePasswordRequest (

     String keycloakId,
     String newPassword) {}
