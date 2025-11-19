package com.daar.core.port.in.usecase.auth;


import com.daar.core.port.in.dto.auth.login.AuthDTO;
import com.daar.core.port.in.dto.auth.login.cmd.*;

import com.daar.core.port.in.dto.auth.login.query.KeycloakData;
import com.daar.core.port.in.dto.auth.login.query.LoginQuery;
import com.daar.core.port.in.dto.auth.login.query.LogoutQuery;
import com.daar.core.port.in.dto.auth.login.query.RefreshTokenQuery;

public interface AuthUseCase {


    // Login / tokens
    AuthDTO<KeycloakData> login(LoginQuery query);  // retourne accessToken pour simplifier
    AuthDTO<KeycloakData> refreshToken(RefreshTokenQuery query);
    AuthDTO<Void> logout(LogoutQuery query);

    // Mots de passe
    AuthDTO<Void> changePassword(ChangePasswordCommand command);
    AuthDTO<Void> resetPassword(ResetPasswordCommand command);
}
