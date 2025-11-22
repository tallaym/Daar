package com.daar.adapter.in.rest.auth.controller.handler;

import com.daar.adapter.in.rest.auth.request.CreateUserRequest;
import com.daar.adapter.in.rest.auth.request.UpdateUserRequest;
import com.daar.adapter.in.rest.auth.mapper.UserMapper;
import com.daar.core.domain.validator.UserValidators;
import com.daar.core.usecase.auth.command.CreateUserCommand;
import com.daar.core.usecase.auth.command.UpdateUserCommand;
import com.daar.core.usecase.auth.dto.UserDTO;
import com.daar.core.usecase.auth.usecase.UserUseCase;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserHandler {

    private final UserUseCase useCase;

    public UserHandler(UserUseCase useCase) {
        this.useCase = useCase;
    }

    public UserDTO insert(CreateUserRequest userRequest){
        UserValidators.newUserValidator(
                userRequest.firstname(),
                userRequest.lastname(),
                userRequest.phone()
        );

        CreateUserCommand command = UserMapper.toCommand(userRequest);
        return useCase.createUser(command);
    }

    public UserDTO update(UUID id, UpdateUserRequest request){
        UserDTO existingUser = useCase.getUserById(id).orElseThrow(() -> new RuntimeException("User not found"));

        UserValidators.updateUserValidator(
                request.firstname(),
                request.lastname(),
                request.origin(),
                request.identityType(),
                request.identityNumber(),
                request.address(),
                request.email(),
                request.phone(),
                request.suspendedUntil(),
                request.updatedBy(),
                request.suspendedBy()
        );

        UpdateUserCommand command = UserMapper.toCommand(id, request);

        return useCase.updateUser(command);
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
