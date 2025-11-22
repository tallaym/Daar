package com.daar.adapter.in.rest.auth.controller;

import com.daar.adapter.in.rest.auth.request.CreateUserRequest;
import com.daar.adapter.in.rest.auth.controller.handler.UserHandler;
import com.daar.adapter.in.rest.auth.mapper.UserMapper;
import com.daar.adapter.in.rest.auth.request.UpdateUserRequest;
import com.daar.adapter.in.rest.auth.response.UserResponse;
import com.daar.core.domain.validator.DateValidators;
import com.daar.core.domain.validator.UserValidators;

import com.daar.core.usecase.auth.dto.UserDTO;
import com.daar.core.usecase.auth.usecase.UserUseCase;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserController {

    private final UserHandler userHandler;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");



    public UserController(UserUseCase useCase) {
        this.userHandler = new UserHandler(useCase);
    }


    private void insertHandler(Context ctx){
        CreateUserRequest userRequest = ctx.bodyAsClass(CreateUserRequest.class);
        UserDTO dto = userHandler.insert(userRequest);
        UserResponse response = UserMapper.toResponse(dto);
        ctx.json(response).status(201);
    }

    private void updateHandler(Context ctx){

        UserValidators.validateUUID(ctx.pathParam("id"));
        UUID id = UUID.fromString(ctx.pathParam("id"));
        UpdateUserRequest request = ctx.bodyAsClass(UpdateUserRequest.class);
        UserDTO dto = userHandler.update(id, request);
        UserResponse response = UserMapper.toResponse(dto);
        ctx.json(response).status(200);
    }


    private void getByIdHandler(Context ctx){
        UserValidators.validateUUID(ctx.pathParam("id"));
            UUID id = UUID.fromString(ctx.pathParam("id"));
            UserDTO dto = userHandler.getById(id);
            UserResponse response = UserMapper.toResponse(dto);
            ctx.json(response).status(200);
    }


    private void getAllHandler(Context ctx){
        List<UserDTO> dtoList = userHandler.getAllUsers();
        List<UserResponse> response = UserMapper.toResponseList(dtoList);
        ctx.json(response).status(200);
    }


    private void getByDateHandler(Context ctx) throws ParseException {
        String dateStr = ctx.queryParam("date");
        DateValidators.validateToInstant(dateStr);
            Date date = DATE_FORMAT.parse(dateStr);

            List<UserDTO> dtoList = userHandler.getByDate(date);
        List<UserResponse> response = UserMapper.toResponseList(dtoList);
        ctx.json(response).status(200);
    }



    private void getByDatesHandler(Context ctx) throws ParseException {

        String startStr = ctx.queryParam("start");
        String endStr = ctx.queryParam("end");

        assert startStr != null;
        assert endStr != null;
        DateValidators.validatePeriod(Instant.parse(startStr), Instant.parse(endStr));
        Date start = DATE_FORMAT.parse(startStr);
        Date end = DATE_FORMAT.parse(endStr);
        List<UserDTO> dtoList = userHandler.getByDate(start, end);
        List<UserResponse> response = UserMapper.toResponseList(dtoList);
        ctx.json(response).status(200);
    }






    //////////////////////* ROUTES *///////////////////////
    public void registerRoutes(Javalin app) {
        app.post("/users", this::insertHandler);
        app.put("/users/{id}", this::updateHandler);
        app.get("/users/{id}", this::getByIdHandler);
        app.get("/users", this::getAllHandler);
        app.get("/users/addedAfter", this::getByDateHandler);
        app.get("/users/addedBetween", this::getByDatesHandler);
    }




}
