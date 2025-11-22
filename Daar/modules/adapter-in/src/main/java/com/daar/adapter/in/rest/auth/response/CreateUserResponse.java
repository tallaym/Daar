package com.daar.adapter.in.rest.auth.response;

import com.daar.core.domain.model.auth.User;

import java.time.Instant;
import java.util.UUID;

public record CreateUserResponse (

     UUID id,

     Instant createdAt){}