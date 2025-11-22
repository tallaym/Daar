package com.daar.adapter.in.rest.auth.mapper;

import com.daar.adapter.in.rest.auth.request.*;

import com.daar.adapter.in.rest.auth.response.AuthResponse;
import com.daar.core.usecase.auth.command.ChangePasswordCommand;
import com.daar.core.usecase.auth.command.ResetPasswordCommand;
import com.daar.core.usecase.auth.dto.AuthDTO;
import com.daar.core.usecase.auth.query.LoginQuery;
import com.daar.core.usecase.auth.query.LogoutQuery;
import com.daar.core.usecase.auth.query.RefreshTokenQuery;

public class AuthMapper {

    /// REST -> DTO


    public static ChangePasswordCommand toCommand(ChangePasswordRequest req) {
        return new ChangePasswordCommand(req.keycloakId(), req.newPassword());
    }

    public static ResetPasswordCommand toCommand(ResetPasswordRequest req) {
        return new ResetPasswordCommand(req.contact());
    }

    public static LoginQuery toQuery(LoginRequest req) {
        return new LoginQuery(req.identifier(), req.password());
    }

    public static LogoutQuery toQuery(LogoutRequest req) {
        return new LogoutQuery(req.token());
    }

    public static RefreshTokenQuery toQuery(RefreshTokenRequest req) {
        return new RefreshTokenQuery(req.refreshToken());
    }

    public static <T> AuthResponse<T> toResponse(AuthDTO<?> dto) {
        return new AuthResponse<>(dto.success(), dto.message());
    }

}
