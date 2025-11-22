package com.daar.adapter.in.rest.auth.response;

public record AuthResponse<T> (

    boolean success,
    String message
){}
