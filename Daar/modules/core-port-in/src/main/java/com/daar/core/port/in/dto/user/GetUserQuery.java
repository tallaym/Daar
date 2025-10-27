package com.daar.core.port.in.dto.user;

import java.util.UUID;

public class GetUserQuery {

    UUID id;

    public GetUserQuery(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
