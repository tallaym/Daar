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
import com.daar.core.port.in.dto.user.*;
import com.daar.core.port.in.usecase.auth.AuthUseCase;
import com.daar.core.port.in.usecase.auth.UserUseCase;
import io.javalin.Javalin;

import java.util.List;
import java.util.UUID;

public class UserController {

    private final UserUseCase useCase;
    private final AuthUseCase authUseCase;



    public UserController(UserUseCase useCase, AuthUseCase authUseCase) {
        this.useCase = useCase;
        this.authUseCase = authUseCase;
    }

    public void registerRoutes(Javalin app) {
        // Define user-related routes here
    }


    public CreateUserResponse createUser(CreateUserRequest userRequest) {
        RegisterRequest authRequest = new RegisterRequest(
                userRequest.getFirstname(),
                userRequest.getLastname(),
                userRequest.getPhone()
        );
        RegisterUserCommand authCommand = AuthMapper.toCommand(authRequest);
        AuthDTO<String> keyCloakId = authUseCase.register(authCommand);


        CreateUserCommand userCommand = UserMapper.toCommand(userRequest);
        UserDTO dto = useCase.create(userCommand, String.valueOf(keyCloakId));
        return UserMapper.toCreateResponse(dto);
    }

    public UpdateUserResponse updateUser(UUID id, String keyCloakId, UpdateUserRequest request) {
        UpdateRequest keyCloakRequest = new UpdateRequest(
                request.getFirstname(),
                request.getLastname(),
                request.getPhone()
        );
        com.daar.core.port.in.dto.login.UpdateUserCommand keyCloakCommand = AuthMapper.toCommand(keyCloakId, keyCloakRequest);
        AuthDTO<Void> authResponse = authUseCase.updateUser(keyCloakCommand);

        UpdateUserCommand command = UserMapper.toCommand(id, request);
        UserDTO dto = useCase.modify(id, command);
        return UserMapper.toUpdateResponse(dto);
    }

    public UserResponse getUserById(GetUserRequest request) {
        GetUserQuery query = UserMapper.toQuery(request);
        UserDTO dto = useCase.getUserById(query).orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toUserResponse(dto);
    }

    public UsersListResponse getAllUsers() {
        return UserMapper.toUsersListResponse(useCase.listUsers());
    }

    public UsersListResponse getUsersAddedAfter(GetAfterDateRequest request) {
        GetAfterDateQuery query = UserMapper.toQuery(request);
        List<UserDTO> dtoList = useCase.addedAfter(query);
        return UserMapper.toUsersListResponse(dtoList);
    }

    public UsersListResponse getUsersAddedBetween(GetBetweenDateRequest request) {
        GetBetweenDateQuery query = UserMapper.toQuery(request);
        List<UserDTO> dtoList = useCase.addedBetween(query);
        return UserMapper.toUsersListResponse(dtoList); // Placeholder
    }
}
