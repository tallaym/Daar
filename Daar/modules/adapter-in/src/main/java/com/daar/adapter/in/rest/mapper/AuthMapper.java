package com.daar.adapter.in.rest.mapper;

import com.daar.adapter.in.rest.request.auth.*;
import com.daar.adapter.in.rest.response.auth.AuthResponse;
import com.daar.core.port.in.dto.login.*;

public class AuthMapper {

    /// REST -> DTO

    public static RegisterUserCommand toCommand(RegisterRequest request) {
        return new RegisterUserCommand(request.getFirstname(), request.getLastname(), request.getPhone());
    }

    public static UpdateUserCommand toCommand(String id, UpdateRequest request) {
        return new UpdateUserCommand(
                id,
                request.getFirstname(),
                request.getLastname(),
                request.getPhone(),
                request.getEmail(),
                request.getPassword()
        );
    }

    public static DeleteCommand toCommand(DeleteRequest request) {
        return new DeleteCommand(request.getKeyCloakId());
    }


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
