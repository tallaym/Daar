package com.daar.adapter.in.rest.auth.mapper;

import com.daar.adapter.in.rest.auth.request.CreateUserRequest;
import com.daar.adapter.in.rest.auth.request.UpdateUserRequest;
import com.daar.adapter.in.rest.auth.response.CreateUserResponse;
import com.daar.adapter.in.rest.auth.response.UserResponse;
import com.daar.core.usecase.auth.command.CreateUserCommand;
import com.daar.core.usecase.auth.command.UpdateUserCommand;
import com.daar.core.usecase.auth.dto.UserDTO;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserMapper {

    // REST -> SERVICES

    public static CreateUserCommand toCommand(CreateUserRequest req) {
        return new CreateUserCommand(
                req.firstname(),
                req.lastname(),
                req.phone(),
                req.keyCloakId(),
                req.createdBy()
        );
    }

    public static UpdateUserCommand toCommand(UUID id, UpdateUserRequest req) {
        return new UpdateUserCommand(
                id,
                req.firstname(),
                req.lastname(),
                req.origin(),
                req.identityType(),
                req.identityNumber(),
                req.address(),
                req.email(),
                req.phone(),
                req.suspendedUntil(),
                req.updatedBy(),
                req.suspendedBy()
        );
    }



    ////////////////////////////////////

    // SERVICES -> REST

    public static CreateUserResponse toSimpleResponse(UserDTO dto) {
        return new CreateUserResponse(
                dto.id(),
                dto.createdAt()
        );
    }


    public static UserResponse toResponse(UserDTO dto) {
        return new UserResponse(
                dto.id(),
                dto.keyCloakId(),
                dto.firstname(),
                dto.lastname(),
                dto.origin(),
                String.valueOf(dto.identityType()),
                dto.identityNumber(),
                dto.address(),
                dto.email(),
                dto.phone(),
                dto.createdAt(),
                dto.updatedAt(),
                dto.suspendedUntil(),
                dto.createdBy(),
                dto.updatedBy(),
                dto.suspendedBy()


        );
    }

    public static List<UserResponse> toResponseList(List<UserDTO> dtos) {
        return dtos.stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }



}

