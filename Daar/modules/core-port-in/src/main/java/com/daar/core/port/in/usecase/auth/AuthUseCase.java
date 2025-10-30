package com.daar.core.port.in.usecase.auth;


import com.daar.core.port.in.dto.login.*;

public interface AuthUseCase {

    AuthDTO<KeyCloakDTO> register(RegisterUserCommand command);

    // Modification des infos
    AuthDTO<Void> updateUser(UpdateUserCommand command);

    // Suppression
    AuthDTO<Void> deleteUser(String keycloakId);

    // Login / tokens
    AuthDTO<String> login(LoginQuery query);  // retourne accessToken pour simplifier
    AuthDTO<String> refreshToken(RefreshTokenQuery query);
    AuthDTO<Void> logout(LogoutQuery query);

    // Mots de passe
    AuthDTO<Void> changePassword(ChangePasswordCommand command);
    AuthDTO<Void> resetPassword(ResetPasswordCommand command);
}
