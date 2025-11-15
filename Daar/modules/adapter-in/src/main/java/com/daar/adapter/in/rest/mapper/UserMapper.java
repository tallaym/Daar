package com.daar.adapter.in.rest.mapper;

import com.daar.adapter.in.rest.request.user.*;
import com.daar.adapter.in.rest.response.user.*;
import com.daar.core.port.in.dto.user.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserMapper {

    // REST -> SERVICES

    public static CreateUserCommand toCommand(CreateUserRequest req, String keycloakId) {
        return new CreateUserCommand(req.getFirstname(), req.getLastname(), req.getPhone(), keycloakId,req.getCreatedBy());
    }

    public static UpdateUserCommand toCommand(UUID userId, UpdateUserRequest req) {
        return new UpdateUserCommand(
                userId,
                req.getFirstname(),
                req.getLastname(),
                req.getOrigin(),
                req.getIdentityType(),
                req.getIdentityNumber(),
                req.getAddress(),
                req.getEmail(),
                req.getPhone(),

                req.getUpdatedAt(),

                req.getSuspendedUntil(),
                req.getUpdatedBy(),
                req.getSuspendedBy()
        );
    }



    ////////////////////////////////////

    // SERVICES -> REST

    public static CreateUserResponse toCreateResponse(UserDTO dto) {
        return new CreateUserResponse(
                dto.getId(),
                dto.getCreatedAt()
        );
    }

    public static UpdateUserResponse toUpdateResponse(UserDTO dto) {
        return new UpdateUserResponse(
                dto.getId(),
                dto.getFirstname(),
                dto.getLastname(),
                dto.getOrigin(),
                String.valueOf(dto.getIdentityType()),
                dto.getIdentityNumber(),
                dto.getAddress(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getUpdatedAt(),
                dto.getSuspendedUntil(),
                dto.getUpdatedBy(),
                dto.getSuspendedBy())
                ;
    }

    public static UserResponse toResponse(UserDTO dto) {
        return new UserResponse(
                dto.getId(),
                dto.getFirstname(),
                dto.getLastname(),
                dto.getOrigin(),
                String.valueOf(dto.getIdentityType()),
                dto.getIdentityNumber(),
                dto.getAddress(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getCreatedAt(),
                dto.getUpdatedAt(),
                dto.getSuspendedUntil(),
                dto.getCreatedBy(),
                dto.getUpdatedBy(),
                dto.getSuspendedBy()

        );
    }

    public static List<UserResponse> toResponseList(List<UserDTO> dtos) {
        return dtos.stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }



}

