package com.daar.core.application.service.auth;


import com.daar.core.domain.model.auth.User;
import com.daar.core.domain.service.UserManagement;
import com.daar.core.port.in.dto.auth.CreateUserCommand;
import com.daar.core.port.in.dto.auth.UpdateUserCommand;
import com.daar.core.port.in.dto.auth.user.UserDTO;
import com.daar.core.port.in.usecase.auth.UserUseCase;
import com.daar.core.domain.port_out.auth.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService implements UserUseCase {


    private final UserRepository ur;
    private final UserManagement um;

    public UserService(UserRepository ur, UserManagement um) {
        this.ur = ur; this.um = um;
    }

    public UserDTO createUser(CreateUserCommand command) {
        User user  = new User(
                command.getFirstname(),
                command.getLastname(),
                command.getPhone(),
                null,
                command.getCreatedBy()
        );
        return mapToDTO(um.createUser(user));
    }

    public UserDTO updateUser(UpdateUserCommand command) {
        User existing = ur.findById(command.getId()).orElseThrow(() -> new RuntimeException("User not found"));

        User updated = new User(
                existing.getId(),
                existing.getKeycloakId(),
                command.getFirstname(),
                command.getLastname(),
                command.getOrigin(),
                command.getIdentityType(),
                command.getIdentityNumber(),
                command.getAddress(),
                command.getEmail(),
                command.getPhone(),
                existing.getCreatedAt(),
                existing.getUpdatedAt(),
                command.getSuspendedUntil(),
                existing.getCreatedBy(),
                command.getUpdatedBy(),
                command.getSuspendedBy()
        );


        return mapToDTO(um.updateUser(existing));
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

