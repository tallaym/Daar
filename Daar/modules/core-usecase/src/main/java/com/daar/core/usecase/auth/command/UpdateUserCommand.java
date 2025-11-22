package com.daar.core.usecase.auth.command;

import com.daar.core.domain.model.auth.User;

import java.time.Instant;
import java.util.UUID;

public record UpdateUserCommand (
        UUID id,
     String firstname,
     String lastname,
     String origin,
     User.IdentityType identityType,
     String identityNumber,
     String address,
    String email,
     String phone,


     Instant suspendedUntil,

     UUID updatedBy,
     UUID suspendedBy) {}
