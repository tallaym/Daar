package com.daar.core.application.auth;


import com.daar.core.domain.model.auth.User;
import com.daar.core.port.in.auth.UserUseCase;
import com.daar.core.port.out.auth.UserRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService implements UserUseCase {


    private final UserRepository ur;

    public UserService(UserRepository ur) {
        this.ur = ur;
    }

    public UUID create(String firstname, String lastname, String phone, UUID createdBy) {
        return ur.insert(firstname, lastname, phone, createdBy).getId();
    }

    public User modify(User u) {
        return ur.update(u);
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

