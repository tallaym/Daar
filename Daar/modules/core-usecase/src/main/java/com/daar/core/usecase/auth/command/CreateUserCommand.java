package com.daar.core.usecase.auth.command;

import java.util.UUID;

public record CreateUserCommand(

    String firstname,
    String lastname,
    String phone,
    String KeycloakId,
    UUID createdBy

){}
