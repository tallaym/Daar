package com.daar.core.usecase.auth.dto;

import com.daar.core.domain.model.auth.User;

import java.time.Instant;
import java.util.UUID;

public record UserDTO (
     UUID id,
     String keyCloakId,
     String firstname,
     String lastname,
     String origin,
     User.IdentityType identityType,
     String identityNumber,
     String address,
     String email,
     String phone,


     Instant createdAt,
     Instant updatedAt,
     Instant suspendedUntil,

     UUID createdBy,
     UUID updatedBy,
     UUID suspendedBy) {}
