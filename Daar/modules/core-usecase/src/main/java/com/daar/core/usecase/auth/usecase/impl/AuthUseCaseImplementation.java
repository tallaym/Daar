package com.daar.core.usecase.auth.usecase.impl;

import com.daar.core.usecase.auth.KeycloakTokenMapper;
import com.daar.core.usecase.auth.dto.AuthDTO;
import com.daar.core.usecase.auth.command.ChangePasswordCommand;
import com.daar.core.usecase.auth.command.ResetPasswordCommand;
import com.daar.core.usecase.auth.query.KeycloakData;
import com.daar.core.usecase.auth.query.LoginQuery;
import com.daar.core.usecase.auth.query.LogoutQuery;
import com.daar.core.usecase.auth.query.RefreshTokenQuery;
import com.daar.core.domain.repository.KeycloakTokenDTO;
import com.daar.core.domain.repository.auth.KeyCloakRepository;
import com.daar.core.usecase.auth.usecase.AuthUseCase;

public class AuthUseCaseImplementation implements AuthUseCase {

    private final KeyCloakRepository kr;

    public AuthUseCaseImplementation(KeyCloakRepository kr) {
        this.kr = kr;
    }



    @Override
    public AuthDTO<KeycloakData> login(LoginQuery query) {
        KeycloakTokenDTO rawData = kr.login(query.identifier(), query.password());
        KeycloakData data = KeycloakTokenMapper.toDomain(rawData);

        if(data != null) {

            return new AuthDTO<>(true, "Login successful. Access Token: ", data, data.accessTokenExpiry(), data.refreshTokenExpiry());
        }   else {
            return new AuthDTO<>(false, "Login failed.", null, null, null);
        }
    }

    @Override
    public AuthDTO<KeycloakData> refreshToken(RefreshTokenQuery query) {
        KeycloakTokenDTO rawData = kr.refreshToken(query.refreshToken());
        KeycloakData data = KeycloakTokenMapper.toDomain(rawData);

        return data != null
                ? new AuthDTO<>(true, "Token refreshed successfully. New Access Token: ", data, data.accessTokenExpiry(), data.refreshTokenExpiry())
                : new AuthDTO<>(false, "Token refresh failed.", null, null,null);
    }

    @Override
    public AuthDTO<Void> logout(LogoutQuery query) {
        boolean loggedOut = kr.logout(query.token());

        return loggedOut
                ? new AuthDTO<>(true, "Logout successful.")
                : new AuthDTO<>(false, "Logout failed.");
    }

    @Override
    public AuthDTO<Void> changePassword(ChangePasswordCommand command) {
        boolean changed = kr.changePassword(command.keycloakId(), command.newPassword());

        return changed
                ? new AuthDTO<>(true, "Password changed successfully.")
                : new AuthDTO<>(false, "Password change failed.");
    }

    @Override
    public AuthDTO<Void> resetPassword(ResetPasswordCommand command) {
        boolean reset = kr.resetPassword(command.contact());
        return reset
                ? new AuthDTO<>(true, "Password reset initiated successfully.")
                : new AuthDTO<>(false, "Password reset initiation failed.");
    }
}
