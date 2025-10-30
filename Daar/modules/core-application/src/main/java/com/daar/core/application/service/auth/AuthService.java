package com.daar.core.application.service.auth;

import com.daar.core.port.in.dto.login.*;
import com.daar.core.port.in.usecase.auth.AuthUseCase;
import com.daar.core.port.out.auth.KeyCloakRepository;
import com.daar.core.port.out.auth.UserRepository;

public class AuthService implements AuthUseCase {

    private final KeyCloakRepository kr;

    public AuthService(KeyCloakRepository kr) {
        this.kr = kr;
    }


    @Override
    public AuthDTO<KeyCloakDTO> register(RegisterUserCommand command) {
        return null;
    }

    @Override
    public AuthDTO<Void> updateUser(UpdateUserCommand command) {
        return null;
    }

    @Override
    public AuthDTO<Void> deleteUser(String keycloakId) {
        return null;
    }

    @Override
    public AuthDTO<String> login(LoginQuery query) {
        return null;
    }

    @Override
    public AuthDTO<String> refreshToken(RefreshTokenQuery query) {
        return null;
    }

    @Override
    public AuthDTO<Void> logout(LogoutQuery query) {
        return null;
    }

    @Override
    public AuthDTO<Void> changePassword(ChangePasswordCommand command) {
        return null;
    }

    @Override
    public AuthDTO<Void> resetPassword(ResetPasswordCommand command) {
        return null;
    }
}
