package com.daar.core.application.service.auth;


import com.daar.core.domain.model.auth.User;
import com.daar.core.port.in.dto.user.CreateUserDTO;
import com.daar.core.port.in.dto.user.UpdateUserDTO;
import com.daar.core.port.in.usecase.auth.UserUseCase;
import com.daar.core.port.out.auth.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService implements UserUseCase {


    private final UserRepository ur;

    public UserService(UserRepository ur) {
        this.ur = ur;
    }

    public UUID create(CreateUserDTO dto) {
        return ur.insert(dto.getFirstname(), dto.getLastname(), dto.getPhone(), dto.getCreatedBy()).getId();
    }

    public User modify(UUID userId, UpdateUserDTO dto) {
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

        return ur.update(user);
    }

    @Override
    public List<User> listUsers() {
        return ur.findAll();
    }

    public List<User> addedAfter(Date start) {
        return ur.findAddedAfter(start);
    }


    public List<User> addedBetween(Date start, Date end) {
        return ur.findAddedBetween(start, end);
    }

    public Optional<User> getUserById(UUID id) {
        return ur.findById(id);
    }




}

