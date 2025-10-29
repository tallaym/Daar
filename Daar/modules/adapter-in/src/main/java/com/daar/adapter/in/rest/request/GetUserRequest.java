package com.daar.adapter.in.rest.request;

import java.util.UUID;

public class GetUserRequest {

    private UUID id;

    public GetUserRequest(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
