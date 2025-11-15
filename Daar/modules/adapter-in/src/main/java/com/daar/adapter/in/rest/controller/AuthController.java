package com.daar.adapter.in.rest.controller;

import com.daar.adapter.in.rest.mapper.AuthMapper;
import com.daar.adapter.in.rest.request.auth.*;
import com.daar.adapter.in.rest.response.auth.AuthResponse;
import com.daar.core.port.in.dto.login.*;
import com.daar.core.port.in.usecase.auth.AuthUseCase;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class AuthController {

    private final AuthUseCase useCase;

    public AuthController(AuthUseCase useCase) {
        this.useCase = useCase;
    }


///////////////////////* METHODS *///////////////////////


    /////////////////////* ROUTES */////////////////////////

    public void registerRoutes(Javalin app) {
        app.post("/auth/register", this::insertHandler);
        app.put("/auth/update/{id}", this::updateHandler);
        app.post("/auth/login", this::loginHandler);
        app.post("/auth/refresh-token", this::refreshTokenHandler);
        app.post("/auth/logout", this::logoutHandler);
        app.delete("/auth/delete", this::deleteHandler);
        app.post("/auth/change-password", this::changePasswordHandler);
        app.post("/auth/reset-password", this::resetPasswordHandler);

    }

    public void insertHandler(Context ctx){
        RegisterRequest request = ctx.bodyAsClass(RegisterRequest.class);
        RegisterUserCommand command = AuthMapper.toCommand(request);
        AuthDTO<String> response = useCase.register(command);
        ctx.json(response).status(201);
    }

    public void updateHandler(Context ctx){
        String id = ctx.pathParam("id");


        UpdateRequest request = ctx.bodyAsClass(UpdateRequest.class);
        UpdateKeyCloakUserCommand command = AuthMapper.toCommand(id,request);
        AuthDTO<Void> response = useCase.updateUser(command);
        ctx.json(response).status(200);
    }

    public void loginHandler(Context ctx){
        LoginRequest request = ctx.bodyAsClass(LoginRequest.class);
        LoginQuery query = AuthMapper.toQuery(request);
        AuthDTO<String> response = useCase.login(query);
        ctx.json(response).status(200);
    }

    public void refreshTokenHandler(Context ctx){
        RefreshTokenRequest request = ctx.bodyAsClass(RefreshTokenRequest.class);
        RefreshTokenQuery query = AuthMapper.toQuery(request);
        AuthDTO<String> response = useCase.refreshToken(query);
        ctx.json(response).status(200);
    }

    public void logoutHandler(Context ctx){
        LogoutRequest request = ctx.bodyAsClass(LogoutRequest.class);
        LogoutQuery query = AuthMapper.toQuery(request);
        AuthDTO<Void> response = useCase.logout(query);
        ctx.json(response).status(200);
    }

    public void deleteHandler(Context ctx){
        DeleteRequest request = ctx.bodyAsClass(DeleteRequest.class);
        DeleteCommand command = AuthMapper.toCommand(request);
        AuthDTO<Void> response = useCase.deleteUser(command);
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
