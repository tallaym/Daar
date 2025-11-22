package com.daar.adapter.in.rest.auth.response;

import com.daar.core.domain.model.auth.User;

import java.time.Instant;
import java.util.UUID;

public record UserResponse (

     UUID id,

     String keyCloakId,
     String firstname,
     String lastname,
     String origin,
     String identityType,
     String identityNumber,
     String address,
     String email,
     String phone,


     Instant createdAt,
     Instant updatedAt,
     Instant suspendedUntil,

     UUID createdBy,
     UUID updatedBy,
     UUID suspendedBy

){}