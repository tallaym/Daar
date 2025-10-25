package com.daar.core.port.in.usecase.auth.activity;

import com.daar.core.domain.model.auth.User;
import com.daar.core.port.in.dto.credential.UpdateCredentialDTO;
import com.daar.core.port.in.dto.login.*;

public interface AuthUseCase {
    LoginResponseDTO login(LoginRequestDTO request);


    void changePassword(String password, UpdateCredentialDTO request);

    LoginResponseDTO refreshToken(RefreshTokenRequestDTO request);

    boolean validateAccessToken(String token);

    User getCurrentUser(String accessToken);

}
