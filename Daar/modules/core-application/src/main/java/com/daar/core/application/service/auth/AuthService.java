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
    public AuthDTO<String> register(RegisterUserCommand command) {

            String keycloakId = kr.createUser(command.getFirstname(), command.getLastname(),
                    command.getPhone());
            if(keycloakId != null) {
                return new AuthDTO<>(true, "Registration successful. Keycloak ID: " + keycloakId);
            }   else {
                return new AuthDTO<>(false, "Registration failed.");
            }
    }


    @Override
    public AuthDTO<Void> updateUser(UpdateUserCommand command) {
        boolean updated = kr.updateUser(command.getKeyCloakId(), command.getFirstname(),
                command.getLastname(), command.getPhone());
        return updated
                ? new AuthDTO<>(true, "User updated successfully.")
                : new AuthDTO<>(false, "User update failed.");
    }

    @Override
    public AuthDTO<Void> deleteUser(DeleteCommand command) {
        boolean deleted = kr.deleteUser(command.getKeyCloakId());

        return deleted
                ? new AuthDTO<>(true, "User deleted successfully.")
                : new AuthDTO<>(false, "User deletion failed.");
    }

    @Override
    public AuthDTO<String> login(LoginQuery query) {
        String accessToken = kr.login(query.getIdentifier(), query.getPassword());

        if(accessToken != null) {
            return new AuthDTO<>(true, "Login successful. Access Token: " + accessToken);
        }   else {
            return new AuthDTO<>(false, "Login failed.");
        }
    }

    @Override
    public AuthDTO<String> refreshToken(RefreshTokenQuery query) {
        String newAccessToken = kr.refreshToken(query.getRefreshToken());

        return newAccessToken != null
                ? new AuthDTO<>(true, "Token refreshed successfully. New Access Token: " + newAccessToken)
                : new AuthDTO<>(false, "Token refresh failed.");
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
