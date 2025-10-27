package com.daar.core.port.in.dto.credential;

import java.util.UUID;

public class GetCredentialQuery {

    UUID userId;

    public GetCredentialQuery(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
