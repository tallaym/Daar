package com.daar.adapter.in.rest.auth.request;

import java.util.UUID;

public record CreateUserRequest (

    String firstname,
    String lastname,
    String phone,
    String keyCloakId,
    UUID createdBy){}