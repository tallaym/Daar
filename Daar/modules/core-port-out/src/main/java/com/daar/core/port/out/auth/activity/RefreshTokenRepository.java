package com.daar.core.port.out.auth.activity;

import com.daar.core.domain.model.auth.activity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {

    RefreshToken save(RefreshToken token);
    Optional<RefreshToken> findByToken(String token);
    void revoke(RefreshToken token);

}
