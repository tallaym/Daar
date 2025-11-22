package com.daar.core.usecase.auth.usecase.impl;


import com.daar.core.domain.model.auth.User;
import com.daar.core.domain.service.UserManagement;
import com.daar.core.usecase.auth.command.CreateUserCommand;
import com.daar.core.usecase.auth.command.UpdateUserCommand;
import com.daar.core.usecase.auth.dto.UserDTO;
import com.daar.core.domain.repository.auth.UserRepository;
import com.daar.core.usecase.auth.usecase.UserUseCase;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserUseCaseImplementation implements UserUseCase {


    private final UserRepository ur;
    private final UserManagement um;

    public UserUseCaseImplementation(UserRepository ur, UserManagement um) {
        this.ur = ur; this.um = um;
    }

    public UserDTO createUser(CreateUserCommand command) {
        User user  = new User(
                command.firstname(),
                command.lastname(),
                command.phone(),
                null,
                command.createdBy()
        );
        return mapToDTO(um.createUser(user));
    }

    public UserDTO updateUser(UpdateUserCommand command) {
        User existing = ur.findById(command.id()).orElseThrow(() -> new RuntimeException("User not found"));

        User updated = new User(
                existing.getId(),
                existing.getKeycloakId(),
                command.firstname(),
                command.lastname(),
                command.origin(),
                command.identityType(),
                command.identityNumber(),
                command.address(),
                command.email(),
                command.phone(),
                existing.getCreatedAt(),
                existing.getUpdatedAt(),
                command.suspendedUntil(),
                existing.getCreatedBy(),
                command.updatedBy(),
                command.suspendedBy()
        );


        return mapToDTO(um.updateUser(updated));
    }

    @Override
    public List<UserDTO> listUsers() {
        List<User> users = ur.findAll();
        return users.stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<UserDTO> addedAfter(Date query) {
        return ur.findAddedAfter(query)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // Utilisateurs ajoutés entre deux dates
    public List<UserDTO> addedBetween(Date start, Date end) {
        return ur.findAddedBetween(start, end)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // Recherche par ID
    public Optional<UserDTO> getUserById(UUID query) {
        return ur.findById(query)
                .map(this::mapToDTO);
    }

    private UserDTO mapToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getKeycloakId(),
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

