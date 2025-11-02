package com.daar.adapter.in.rest.mapper;

import com.daar.adapter.in.rest.request.auth.*;
import com.daar.adapter.in.rest.response.auth.AuthResponse;
import com.daar.core.port.in.dto.login.*;

public class AuthMapper {

    /// REST -> DTO

    public RegisterUserCommand toCommand(RegisterRequest request) {
        return new RegisterUserCommand(request.getFirstname(), request.getLastname(), request.getPhone());
    }

    public UpdateUserCommand toCommand(UpdateRequest request) {
        return new UpdateUserCommand(
                request.getKeyCloakId(),
                request.getFirstname(),
                request.getLastname(),
                request.getPhone(),
                request.getEmail(),
                request.getPassword()
        );
    }

    public DeleteCommand toCommand(DeleteRequest request) {
        return new DeleteCommand(request.getKeyCloakId());
    }


    public ChangePasswordCommand toCommand(ChangePasswordRequest req) {
        return new ChangePasswordCommand(req.getKeycloakId(), req.getNewPassword());
    }

    public ResetPasswordCommand toCommand(ResetPasswordRequest req) {
        return new ResetPasswordCommand(req.getContact());
    }

    public LoginQuery toQuery(LoginRequest req) {
        return new LoginQuery(req.getIdentifier(), req.getPassword());
    }

    public LogoutQuery toQuery(LogoutRequest req) {
        return new LogoutQuery(req.getRefreshToken());
    }

    public RefreshTokenQuery toQuery(RefreshTokenRequest req) {
        return new RefreshTokenQuery(req.getRefreshToken());
    }

    public <T> AuthResponse<T> toResponse(AuthDTO<?> dto) {
        return new AuthResponse<>(dto.isSuccess(), dto.getMessage());
    }

}
