package com.daar.adapter.in.rest.controller;

import com.daar.adapter.in.rest.mapper.AuthMapper;
import com.daar.adapter.in.rest.request.auth.*;
import com.daar.adapter.in.rest.response.auth.AuthResponse;
import com.daar.core.port.in.dto.login.*;
import com.daar.core.port.in.usecase.auth.AuthUseCase;
import io.javalin.Javalin;

public class AuthController {

    private final AuthUseCase useCase;

    public AuthController(AuthUseCase useCase) {
        this.useCase = useCase;
    }

    public void registerRoutes(Javalin app) {

    }

    public AuthResponse<String> register(RegisterRequest request){
        RegisterUserCommand command = AuthMapper.toCommand(request);
        AuthDTO<String> result = useCase.register(command);
        return AuthMapper.toResponse(result);
    }

    public AuthResponse<String> login(LoginRequest request){
        LoginQuery query = AuthMapper.toQuery(request);
        AuthDTO<String> result = useCase.login(query);
        return AuthMapper.toResponse(result);
    }

    public AuthResponse<String> refreshToken(RefreshTokenRequest request){
        RefreshTokenQuery query = AuthMapper.toQuery(request);
        AuthDTO<String> result = useCase.refreshToken(query);
        return AuthMapper.toResponse(result);
    }

    public AuthResponse<Void> logout(LogoutRequest request){
        LogoutQuery query = AuthMapper.toQuery(request);
        AuthDTO<Void> result = useCase.logout(query);
        return AuthMapper.toResponse(result);
    }

    public AuthResponse<Void> deleteUser(DeleteRequest request){
        DeleteCommand command = AuthMapper.toCommand(request);
        AuthDTO<Void> result = useCase.deleteUser(command);
        return AuthMapper.toResponse(result);
    }

    public AuthResponse<Void> updateUser(String id, UpdateRequest request){
        UpdateUserCommand command = AuthMapper.toCommand(id,request);
        AuthDTO<Void> result = useCase.updateUser(command);
        return AuthMapper.toResponse(result);
    }

    public AuthResponse<Void> changePassword(ChangePasswordRequest request){
        ChangePasswordCommand command = AuthMapper.toCommand(request);
        AuthDTO<Void> result = useCase.changePassword(command);
        return AuthMapper.toResponse(result);
    }

    public AuthResponse<Void> resetPassword(ResetPasswordRequest request){
        ResetPasswordCommand command = AuthMapper.toCommand(request);
        AuthDTO<Void> result = useCase.resetPassword(command);
        return AuthMapper.toResponse(result);
    }
}
