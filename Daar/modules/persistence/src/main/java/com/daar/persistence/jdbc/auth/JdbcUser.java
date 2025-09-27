package com.daar.persistence.jdbc.auth;


import com.daar.core.model.auth.User;
import com.daar.core.port.out.auth.UserRepository;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import java.util.*;



public class JdbcUser implements UserRepository {


    private final Connection connection;

    public JdbcUser(Connection connection) {
        this.connection = connection;
    }
    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public List<User> findAddedAfter(Date start) {
        return null;
    }

    @Override
    public List<User> findAddedBetween(Date start, Date end) {
        return null;
    }
}
