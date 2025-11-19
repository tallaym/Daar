package com.daar.adapter.in.rest.mapper;

import com.daar.adapter.in.rest.request.auth.*;
import com.daar.adapter.in.rest.response.auth.AuthResponse;
import com.daar.core.port.in.dto.auth.login.AuthDTO;
import com.daar.core.port.in.dto.auth.login.cmd.*;
import com.daar.core.port.in.dto.auth.login.query.LoginQuery;
import com.daar.core.port.in.dto.auth.login.query.LogoutQuery;
import com.daar.core.port.in.dto.auth.login.query.RefreshTokenQuery;

public class AuthMapper {

    /// REST -> DTO


    public static ChangePasswordCommand toCommand(ChangePasswordRequest req) {
        return new ChangePasswordCommand(req.getKeycloakId(), req.getNewPassword());
    }

    public static ResetPasswordCommand toCommand(ResetPasswordRequest req) {
        return new ResetPasswordCommand(req.getContact());
    }

    public static LoginQuery toQuery(LoginRequest req) {
        return new LoginQuery(req.getIdentifier(), req.getPassword());
    }

    public static LogoutQuery toQuery(LogoutRequest req) {
        return new LogoutQuery(req.getRefreshToken());
    }

    public static RefreshTokenQuery toQuery(RefreshTokenRequest req) {
        return new RefreshTokenQuery(req.getRefreshToken());
    }

    public static <T> AuthResponse<T> toResponse(AuthDTO<?> dto) {
        return new AuthResponse<>(dto.isSuccess(), dto.getMessage());
    }

}
