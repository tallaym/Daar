package com.daar.core.usecase.auth.usecase;


import com.daar.core.usecase.auth.dto.AuthDTO;
import com.daar.core.usecase.auth.command.ChangePasswordCommand;
import com.daar.core.usecase.auth.command.ResetPasswordCommand;
import com.daar.core.usecase.auth.query.KeycloakData;
import com.daar.core.usecase.auth.query.LogoutQuery;
import com.daar.core.port.in.dto.auth.login.cmd.*;

import com.daar.core.usecase.auth.query.LoginQuery;
import com.daar.core.usecase.auth.query.RefreshTokenQuery;

public interface AuthUseCase {


    // Login / tokens
    AuthDTO<KeycloakData> login(LoginQuery query);  // retourne accessToken pour simplifier
    AuthDTO<KeycloakData> refreshToken(RefreshTokenQuery query);
    AuthDTO<Void> logout(LogoutQuery query);

    // Mots de passe
    AuthDTO<Void> changePassword(ChangePasswordCommand command);
    AuthDTO<Void> resetPassword(ResetPasswordCommand command);
}
