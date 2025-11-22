package com.daar.core.usecase.auth.dto;

import java.util.UUID;

public record BasicUserDTO (

     UUID id,
     String firstname,
     String lastname,
     String phone,
     String email) {}



