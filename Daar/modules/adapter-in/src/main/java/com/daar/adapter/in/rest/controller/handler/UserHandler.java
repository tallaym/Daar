package com.daar.adapter.in.rest.controller.handler;

import com.daar.adapter.in.rest.mapper.AuthMapper;
import com.daar.adapter.in.rest.mapper.UserMapper;
import com.daar.adapter.in.rest.request.auth.RegisterRequest;
import com.daar.adapter.in.rest.request.auth.UpdateRequest;
import com.daar.adapter.in.rest.request.user.CreateUserRequest;
import com.daar.adapter.in.rest.request.user.UpdateUserRequest;
import com.daar.core.port.in.dto.user.UserDTO;
import com.daar.core.domain.validator.UserValidators;
import com.daar.core.port.in.dto.login.AuthDTO;
import com.daar.core.port.in.dto.login.RegisterUserCommand;
import com.daar.core.port.in.dto.login.UpdateKeyCloakUserCommand;
import com.daar.core.port.in.dto.user.*;
import com.daar.core.port.in.usecase.auth.AuthUseCase;
import com.daar.core.port.in.usecase.auth.UserUseCase;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserHandler {

    private final UserUseCase useCase;
    private final AuthUseCase authUseCase;

    public UserHandler(UserUseCase useCase, AuthUseCase authUseCase) {
        this.useCase = useCase;
        this.authUseCase = authUseCase;
    }

    public UserDTO insert(CreateUserRequest userRequest){
        UserValidators.newUserValidator(
                userRequest.getFirstname(),
                userRequest.getLastname(),
                userRequest.getPhone()
        );

        RegisterRequest authRequest = new RegisterRequest(
                userRequest.getFirstname(),
                userRequest.getLastname(),
                userRequest.getPhone()
        );

        RegisterUserCommand authCommand = AuthMapper.toCommand(authRequest);
        AuthDTO<String> keyCloakId = authUseCase.register(authCommand);

        CreateUserCommand command = UserMapper.toCommand(userRequest, keyCloakId.getMessage());
        return useCase.create(command);
    }

    public UserDTO update(UUID id, UpdateUserRequest request){
        UserDTO existingUser = useCase.getUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
        String keyCloakId = existingUser.getKeyCloakId();

        UserValidators.updateUserValidator(
                request.getFirstname(),
                request.getLastname(),
                request.getOrigin(),
                request.getIdentityType(),
                request.getIdentityNumber(),
                request.getAddress(),
                request.getEmail(),
                request.getPhone(),
                request.getUpdatedAt(),
                request.getSuspendedUntil(),
                request.getUpdatedBy(),
                request.getSuspendedBy()
        );
        UpdateRequest keyCloakRequest = new UpdateRequest(

                request.getFirstname(),
                request.getLastname(),
                request.getPhone()
        );

        UpdateKeyCloakUserCommand keyCloakCommand = AuthMapper.toCommand(keyCloakId, keyCloakRequest);
        AuthDTO<Void> authResponse = authUseCase.updateUser(keyCloakCommand);

        UpdateUserCommand command = UserMapper.toCommand(id, request);

        return useCase.modify(id, command);
    }

    public UserDTO getById(UUID id){
        return useCase.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public List<UserDTO> getAllUsers(){
        return useCase.listUsers();
    }

    public List<UserDTO> getByDate(Date date){
        return useCase.addedAfter(date);
    }

    public List<UserDTO> getByDate(Date start, Date end){
        return useCase.addedBetween(start, end);
    }


}
