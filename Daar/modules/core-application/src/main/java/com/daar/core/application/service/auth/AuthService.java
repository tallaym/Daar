package com.daar.core.application.service.auth;

import com.daar.core.application.service.KeycloakTokenMapper;
import com.daar.core.port.in.dto.auth.login.AuthDTO;
import com.daar.core.port.in.dto.auth.login.cmd.*;
import com.daar.core.port.in.dto.auth.login.query.KeycloakData;
import com.daar.core.port.in.dto.auth.login.query.LoginQuery;
import com.daar.core.port.in.dto.auth.login.query.LogoutQuery;
import com.daar.core.port.in.dto.auth.login.query.RefreshTokenQuery;
import com.daar.core.port.in.usecase.auth.AuthUseCase;
import com.daar.core.domain.port_out.KeycloakTokenDTO;
import com.daar.core.domain.port_out.auth.KeyCloakRepository;

public class AuthService implements AuthUseCase {

    private final KeyCloakRepository kr;

    public AuthService(KeyCloakRepository kr) {
        this.kr = kr;
    }



    @Override
    public AuthDTO<KeycloakData> login(LoginQuery query) {
        KeycloakTokenDTO rawData = kr.login(query.getIdentifier(), query.getPassword());
        KeycloakData data = KeycloakTokenMapper.toDomain(rawData);

        if(data != null) {

            return new AuthDTO<>(true, "Login successful. Access Token: ", data, data.accessTokenExpiry(), data.refreshTokenExpiry());
        }   else {
            return new AuthDTO<>(false, "Login failed.", null, null, null);
        }
    }

    @Override
    public AuthDTO<KeycloakData> refreshToken(RefreshTokenQuery query) {
        KeycloakTokenDTO rawData = kr.refreshToken(query.getRefreshToken());
        KeycloakData data = KeycloakTokenMapper.toDomain(rawData);

        return data != null
                ? new AuthDTO<>(true, "Token refreshed successfully. New Access Token: ", data, data.accessTokenExpiry(), data.refreshTokenExpiry())
                : new AuthDTO<>(false, "Token refresh failed.", null, null,null);
    }

    @Override
    public AuthDTO<Void> logout(LogoutQuery query) {
        boolean loggedOut = kr.logout(query.getRefreshToken());

        return loggedOut
                ? new AuthDTO<>(true, "Logout successful.")
                : new AuthDTO<>(false, "Logout failed.");
    }

    @Override
    public AuthDTO<Void> changePassword(ChangePasswordCommand command) {
        boolean changed = kr.changePassword(command.getKeycloakId(), command.getNewPassword());

        return changed
                ? new AuthDTO<>(true, "Password changed successfully.")
                : new AuthDTO<>(false, "Password change failed.");
    }

    @Override
    public AuthDTO<Void> resetPassword(ResetPasswordCommand command) {
        boolean reset = kr.resetPassword(command.getContact());
        return reset
                ? new AuthDTO<>(true, "Password reset initiated successfully.")
                : new AuthDTO<>(false, "Password reset initiation failed.");
    }
}
