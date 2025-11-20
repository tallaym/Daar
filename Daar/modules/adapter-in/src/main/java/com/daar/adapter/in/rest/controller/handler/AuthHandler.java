package com.daar.adapter.in.rest.controller.handler;

import com.daar.adapter.in.rest.mapper.AuthMapper;
import com.daar.adapter.in.rest.request.auth.*;
import com.daar.core.port.in.dto.auth.login.AuthDTO;
import com.daar.core.port.in.dto.auth.login.cmd.ChangePasswordCommand;
import com.daar.core.port.in.dto.auth.login.cmd.ResetPasswordCommand;
import com.daar.core.port.in.dto.auth.login.query.KeycloakData;
import com.daar.core.port.in.dto.auth.login.query.LoginQuery;
import com.daar.core.port.in.dto.auth.login.query.LogoutQuery;
import com.daar.core.port.in.dto.auth.login.query.RefreshTokenQuery;
import com.daar.core.port.in.usecase.auth.AuthUseCase;

public class AuthHandler {

    private final AuthUseCase useCase;

    public AuthHandler(AuthUseCase useCase) {
        this.useCase = useCase;
    }

    public AuthDTO<KeycloakData> login(LoginRequest request){
        LoginQuery query = AuthMapper.toQuery(request);
        return useCase.login(query);
    }

    public AuthDTO<KeycloakData> refreshToken(RefreshTokenRequest request){
        RefreshTokenQuery query = AuthMapper.toQuery(request);
        return useCase.refreshToken(query);
    }

    public AuthDTO<Void> logout(LogoutRequest request){
        LogoutQuery query = AuthMapper.toQuery(request);
        return useCase.logout(query);
    }

    // Mots de passe
    public AuthDTO<Void> changePassword(ChangePasswordRequest request) {
        ChangePasswordCommand command = AuthMapper.toCommand(request);
        return useCase.changePassword(command);
    }

    public AuthDTO<Void> resetPassword(ResetPasswordRequest request) {
        ResetPasswordCommand command = AuthMapper.toCommand(request);
        return useCase.resetPassword(command);
    }


}
