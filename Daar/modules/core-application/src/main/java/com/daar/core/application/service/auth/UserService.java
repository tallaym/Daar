package com.daar.core.application.service.auth;


import com.daar.core.domain.model.auth.User;
import com.daar.core.port.in.dto.user.*;
import com.daar.core.port.in.usecase.auth.UserUseCase;
import com.daar.core.port.out.auth.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

public class UserService implements UserUseCase {


    private final UserRepository ur;

    public UserService(UserRepository ur) {
        this.ur = ur;
    }

    public UserDTO create(CreateUserCommand command) {
        User u = new User(command.getFirstname(), command.getLastname(), command.getPhone(), command.getCreatedBy());
        ur.insert(u);

        return new UserDTO(u.getId(),u.getFirstname(), u.getLastname(), u.getPhone(),u.getCreatedBy());
    }

    public UserDTO modify(UUID userId, UpdateUserCommand dto) {
        User user = ur.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));

        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setOrigin(dto.getOrigin());
        user.setIdentityType(dto.getIdentityType());
        user.setIdentityNumber(dto.getIdentityNumber());
        user.setEmail(dto.getEmail());
        user.setAddress(dto.getAddress());
        user.setPhone(dto.getPhone());
        user.setUpdatedAt(dto.getUpdatedAt());
        user.setUpdatedBy(dto.getUpdatedBy());
        user.setSuspendedBy(dto.getSuspendedBy());
        user.setSuspendedUntil(dto.getSuspendedUntil());

        ur.update(user);

        return new UserDTO(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getOrigin(),
                user.getIdentityType(),
                user.getIdentityNumber(),
                user.getAddress(),
                user.getEmail(),
                user.getPhone(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getSuspendedUntil(),
                user.getCreatedBy(),
                user.getUpdatedBy(),
                user.getSuspendedBy());
    }

    @Override
    public List<UserDTO> listUsers() {
        List<User> users = ur.findAll();
        return users.stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getOrigin(),
                        user.getIdentityType(),
                        user.getIdentityNumber(),
                        user.getAddress(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getCreatedAt(),
                        user.getUpdatedAt(),
                        user.getSuspendedUntil(),
                        user.getCreatedBy(),
                        user.getUpdatedBy(),
                        user.getSuspendedBy())
                )
                .toList();
    }

    public List<UserDTO> addedAfter(GetAfterDateQuery query) {
        return ur.findAddedAfter(query.getDate())
                .stream()
                .map(user -> mapToDTO(user))
                .toList();
    }

    // Utilisateurs ajoutés entre deux dates
    public List<UserDTO> addedBetween(GetBetweenDateQuery query) {
        return ur.findAddedBetween(query.getStart(), query.getEnd())
                .stream()
                .map(user -> mapToDTO(user))
                .toList();
    }

    // Recherche par ID
    public Optional<UserDTO> getUserById(GetUserQuery query) {
        return ur.findById(query.getId())
                .map(user -> mapToDTO(user));
    }

    private UserDTO mapToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getOrigin(),
                user.getIdentityType(),
                user.getIdentityNumber(),
                user.getAddress(),
                user.getEmail(),
                user.getPhone(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getSuspendedUntil(),
                user.getCreatedBy(),
                user.getUpdatedBy(),
                user.getSuspendedBy());


    }


}

