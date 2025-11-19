package com.daar.adapter.in.rest.controller;

import com.daar.adapter.in.rest.controller.handler.AuthHandler;
import com.daar.adapter.in.rest.mapper.AuthMapper;
import com.daar.adapter.in.rest.request.auth.*;
import com.daar.adapter.in.rest.response.auth.AuthResponse;
import com.daar.core.port.in.dto.auth.login.AuthDTO;
import com.daar.core.port.in.dto.auth.login.cmd.*;
import com.daar.core.port.in.dto.auth.login.query.KeycloakData;
import com.daar.core.port.in.dto.auth.login.query.LoginQuery;
import com.daar.core.port.in.dto.auth.login.query.LogoutQuery;
import com.daar.core.port.in.dto.auth.login.query.RefreshTokenQuery;
import com.daar.core.port.in.usecase.auth.AuthUseCase;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class AuthController {

    private final AuthHandler handler;


    public AuthController(AuthHandler handler) {
        this.handler = handler;
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
        AuthDTO<KeycloakData> response = handler.login(request);

        AuthResponse<> authResponse = AuthMapper.toResponse(response);
        ctx.json(response).status(200);
    }

    public void refreshTokenHandler(Context ctx){
        RefreshTokenRequest request = ctx.bodyAsClass(RefreshTokenRequest.class);
        RefreshTokenQuery query = AuthMapper.toQuery(request);
        AuthDTO<KeycloakData> response = handler.refreshToken(query);
        ctx.json(response).status(200);
    }

    public void logoutHandler(Context ctx){
        LogoutRequest request = ctx.bodyAsClass(LogoutRequest.class);
        LogoutQuery query = AuthMapper.toQuery(request);
        AuthDTO<Void> response = useCase.logout(query);
        ctx.json(response).status(200);
    }


    public void changePasswordHandler(Context ctx){
        ChangePasswordRequest request = ctx.bodyAsClass(ChangePasswordRequest.class);
        ChangePasswordCommand command = AuthMapper.toCommand(request);
        AuthDTO<Void> response = useCase.changePassword(command);
        ctx.json(response).status(200);
    }

    public void resetPasswordHandler(Context ctx){
        ResetPasswordRequest request = ctx.bodyAsClass(ResetPasswordRequest.class);
        ResetPasswordCommand command = AuthMapper.toCommand(request);
        AuthDTO<Void> response = useCase.resetPassword(command);
        ctx.json(response).status(200);
    }

}
