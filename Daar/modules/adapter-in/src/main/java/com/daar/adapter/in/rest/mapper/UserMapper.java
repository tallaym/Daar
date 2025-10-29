package com.daar.adapter.in.rest.mapper;

import com.daar.adapter.in.rest.request.*;
import com.daar.adapter.in.rest.response.CreateUserResponse;
import com.daar.adapter.in.rest.response.UpdateUserResponse;
import com.daar.adapter.in.rest.response.UserResponse;
import com.daar.adapter.in.rest.response.UsersListResponse;
import com.daar.core.port.in.dto.user.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserMapper {

    // REST -> SERVICES

    public static CreateUserCommand toCommand(CreateUserRequest req) {
        return new CreateUserCommand(req.getFirstname(), req.getLastname(), req.getPhone(), req.getCreatedBy());
    }

    public static UpdateUserCommand toCommand(UpdateUserRequest req) {
        return new UpdateUserCommand(
                req.getId(),
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

    public static GetUserQuery toQuery(GetUserRequest req) {
        return new GetUserQuery(req.getId());
    }

    public static GetAfterDateQuery toQuery(GetAfterDateRequest req) {
        return new GetAfterDateQuery(req.getDate());
    }

    public static GetBetweenDateQuery toQuery(GetBetweenDateRequest req) {
        return new GetBetweenDateQuery(req.getStart(), req.getEnd());
    }

    ////////////////////////////////////

    // SERVICES -> REST

    public static CreateUserResponse toCreateResponse(UserDTO dto) {
        return new CreateUserResponse(
                dto.getId(),
                dto.getKeyCloakId(),
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

    public static UserResponse toUserResponse(UserDTO dto) {
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
                dto.getSuspendedBy())
                ;
        );
    }

    public static UsersListResponse toUsersListResponse(List<UserDTO> dtos) {
        List<UserResponse> responses = dtos.stream()
                .map(UserMapper::toUserResponse)
                .collect(Collectors.toList());
        return new UsersListResponse(responses);
    }



}

