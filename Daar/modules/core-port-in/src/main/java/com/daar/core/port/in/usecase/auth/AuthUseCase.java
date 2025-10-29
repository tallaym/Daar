package com.daar.core.port.in.usecase.auth;

import com.daar.core.domain.model.auth.User;
import com.daar.core.port.in.dto.credential.UpdateCredentialCommand;
import com.daar.core.port.in.dto.login.*;

public interface AuthUseCase {
    LoginResponseDTO login(LoginRequestDTO request);


    void changePassword(String password, UpdateCredentialCommand request);

    LoginResponseDTO refreshToken(RefreshTokenRequestDTO request);

    boolean validateAccessToken(String token);

    User getCurrentUser(String accessToken);

}
