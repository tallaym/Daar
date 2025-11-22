package com.daar.adapter.in.rest.auth.controller;

import com.daar.adapter.in.rest.auth.controller.handler.AuthHandler;
import com.daar.adapter.in.rest.auth.mapper.AuthMapper;
import com.daar.adapter.in.rest.auth.request.*;
import com.daar.adapter.in.rest.request.*;
import com.daar.adapter.in.rest.auth.response.AuthResponse;
import com.daar.core.usecase.auth.dto.AuthDTO;
import com.daar.core.usecase.auth.query.KeycloakData;
import com.daar.core.usecase.auth.usecase.AuthUseCase;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class AuthController {

    private final AuthHandler handler;


    public AuthController(AuthUseCase useCase) {
        this.handler = new AuthHandler(useCase);
    }


///////////////////////* METHODS *///////////////////////


    /////////////////////* ROUTES */////////////////////////

    public void registerRoutes(Javalin app) {
        app.post("/auth/login", this::loginHandler);
        app.post("/auth/refresh-token", this::refreshTokenHandler);
        app.post("/auth/logout", this::logoutHandler);
        app.post("/auth/change-password", this::changePasswordHandler);
        app.post("/auth/reset-password", this::resetPasswordHandler);

    }


    public void loginHandler(Context ctx){
        LoginRequest request = ctx.bodyAsClass(LoginRequest.class);
        AuthDTO<KeycloakData> dto = handler.login(request);
        AuthResponse<Object> response = AuthMapper.toResponse(dto);
        ctx.json(response).status(200);
    }

    public void refreshTokenHandler(Context ctx){
        RefreshTokenRequest request = ctx.bodyAsClass(RefreshTokenRequest.class);
        AuthDTO<KeycloakData> dto = handler.refreshToken(request);
        AuthResponse<Object> response = AuthMapper.toResponse(dto);
        ctx.json(response).status(200);
    }

    public void logoutHandler(Context ctx){
        LogoutRequest request = ctx.bodyAsClass(LogoutRequest.class);
        AuthDTO<Void> dto = handler.logout(request);
        AuthResponse<Object> response = AuthMapper.toResponse(dto);
        ctx.json(response).status(200);
    }


    public void changePasswordHandler(Context ctx){
        ChangePasswordRequest request = ctx.bodyAsClass(ChangePasswordRequest.class);
        AuthDTO<Void> dto = handler.changePassword(request);
        AuthResponse<Object> response = AuthMapper.toResponse(dto);
        ctx.json(response).status(200);
    }

    public void resetPasswordHandler(Context ctx){
        ResetPasswordRequest request = ctx.bodyAsClass(ResetPasswordRequest.class);
        AuthDTO<Void> dto = handler.resetPassword(request);
        AuthResponse<Object> response = AuthMapper.toResponse(dto);
        ctx.json(response).status(200);
    }

}
