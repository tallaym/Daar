package com.daar.adapter.in.rest.controller;

import com.daar.adapter.in.rest.mapper.AuthMapper;
import com.daar.adapter.in.rest.mapper.UserMapper;
import com.daar.adapter.in.rest.request.auth.RegisterRequest;
import com.daar.adapter.in.rest.request.auth.UpdateRequest;
import com.daar.adapter.in.rest.request.user.*;
import com.daar.adapter.in.rest.response.auth.AuthResponse;
import com.daar.adapter.in.rest.response.user.CreateUserResponse;
import com.daar.adapter.in.rest.response.user.UpdateUserResponse;
import com.daar.adapter.in.rest.response.user.UserResponse;
import com.daar.adapter.in.rest.response.user.UsersListResponse;
import com.daar.core.domain.model.auth.User;
import com.daar.core.port.in.dto.login.AuthDTO;
import com.daar.core.port.in.dto.login.RegisterUserCommand;
import com.daar.core.port.in.dto.login.UpdateKeyCloakUserCommand;
import com.daar.core.port.in.dto.user.*;
import com.daar.core.port.in.usecase.auth.AuthUseCase;
import com.daar.core.port.in.usecase.auth.UserUseCase;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.eclipse.jetty.server.handler.ContextHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static io.javalin.apibuilder.ApiBuilder.*;

public class UserController {

    private final UserUseCase useCase;
    private final AuthUseCase authUseCase;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");



    public UserController(UserUseCase useCase, AuthUseCase authUseCase) {
        this.useCase = useCase;
        this.authUseCase = authUseCase;
    }


    private void insertHandler(Context ctx){
        CreateUserRequest userRequest = ctx.bodyAsClass(CreateUserRequest.class);

        RegisterRequest authRequest = new RegisterRequest(
                userRequest.getFirstname(),
                userRequest.getLastname(),
                userRequest.getPhone()
        );
        RegisterUserCommand authCommand = AuthMapper.toCommand(authRequest);
        AuthDTO<String> keyCloakId = authUseCase.register(authCommand);

        CreateUserCommand command = UserMapper.toCommand(userRequest, keyCloakId.getMessage());
        UserDTO dto = useCase.create(command);
        ctx.json(dto).status(201);
    }




    private void updateHandler(Context ctx){
        UUID id = UUID.fromString(ctx.pathParam("id"));

        UserDTO existingUser = useCase.getUserById(new GetUserQuery(id)).orElseThrow(() -> new RuntimeException("User not found"));
        String keyCloakId = existingUser.getKeyCloakId();
        UpdateUserRequest request = ctx.bodyAsClass(UpdateUserRequest.class);

        UpdateRequest keyCloakRequest = new UpdateRequest(

                request.getFirstname(),
                request.getLastname(),
                request.getPhone()
        );

        UpdateKeyCloakUserCommand keyCloakCommand = AuthMapper.toCommand(keyCloakId, keyCloakRequest);
        AuthDTO<Void> authResponse = authUseCase.updateUser(keyCloakCommand);

        UpdateUserCommand command = UserMapper.toCommand(id, request);
        UserDTO dto = useCase.modify(id, command);

        ctx.json(dto).status(200);
    }


    private void userByIdHandler(Context ctx){
        UUID id = UUID.fromString(ctx.pathParam("id"));
        GetUserRequest request = new GetUserRequest(id);
        GetUserQuery query = UserMapper.toQuery(request);
        UserDTO dto = useCase.getUserById(query).orElseThrow(() -> new RuntimeException("User not found"));
        ctx.json(dto).status(200);
    }


    public void allUsersHandler(Context ctx){
        List<UserDTO> dtoList = useCase.listUsers();
        ctx.json(dtoList).status(200);
    }


    public void getUsersAddedAfterHandler(Context ctx) throws ParseException {
        String dateStr = ctx.queryParam("date");
            Date date = DATE_FORMAT.parse(dateStr);
        GetAfterDateRequest request = new GetAfterDateRequest(date);
        GetAfterDateQuery query = UserMapper.toQuery(request);
        List<UserDTO> dtoList = useCase.addedAfter(query);
        ctx.json(dtoList).status(200);
    }



    public void getUsersAddedBetweenHandler(Context ctx) throws ParseException {
        String startStr = ctx.queryParam("start");
            Date start = DATE_FORMAT.parse(startStr);
        String endStr = ctx.queryParam("end");
            Date end = DATE_FORMAT.parse(endStr);

        GetBetweenDateRequest request = new GetBetweenDateRequest(start, end);
        GetBetweenDateQuery query = UserMapper.toQuery(request);
        List<UserDTO> dtoList = useCase.addedBetween(query);
        ctx.json(dtoList).status(200);
    }






    //////////////////////* ROUTES *///////////////////////
    public void registerRoutes(Javalin app) {
        app.post("/users", this::insertHandler);
        app.put("/users/:id", this::updateHandler);
        app.get("/users/:id", this::userByIdHandler);
        app.get("/users", this::allUsersHandler);
        app.get("/users/addedAfter", this::getUsersAddedAfterHandler);
        app.get("/users/addedBetween", this::getUsersAddedBetweenHandler);

////
//        app.routes(() -> {
//            path("users", () -> {
//                app.post("", this::insertHandler);
//                app.get("", this::completeReadHandler);
//                app.get("added-after", this::getUsersAddedAfterHandler);
//                app.get("added-between", this::getUsersAddedBetweenHandler);
//                path(":id", () -> {
//                    app.get("", this::readHandler);
//                    app.put("", this::updateHandler);
//                });
//            });
//        });
    }




}
