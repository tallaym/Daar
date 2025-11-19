package com.daar.adapter.in.rest.controller.handler;

import com.daar.adapter.in.rest.mapper.AuthMapper;
import com.daar.adapter.in.rest.mapper.UserMapper;
import com.daar.adapter.in.rest.request.user.CreateUserRequest;
import com.daar.adapter.in.rest.request.user.UpdateUserRequest;
import com.daar.core.port.in.dto.auth.CreateUserCommand;
import com.daar.core.port.in.dto.auth.UpdateUserCommand;
import com.daar.core.port.in.dto.auth.user.UserDTO;
import com.daar.core.domain.validator.UserValidators;
import com.daar.core.port.in.usecase.auth.UserUseCase;

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
                userRequest.getFirstname(),
                userRequest.getLastname(),
                userRequest.getPhone()
        );

        CreateUserCommand command = UserMapper.toCommand(userRequest);
        return useCase.createUser(command);
    }

    public UserDTO update(UUID id, UpdateUserRequest request){
        UserDTO existingUser = useCase.getUserById(id).orElseThrow(() -> new RuntimeException("User not found"));

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
