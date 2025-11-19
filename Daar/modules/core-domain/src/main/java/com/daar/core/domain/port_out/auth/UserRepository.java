package com.daar.core.domain.port_out.auth;


import com.daar.core.domain.model.auth.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User insert(User user);
    User update(User user);
    Optional<User> findById(UUID id);
    List<User> findAll();
    List<User> findAddedAfter(Date start);
    List<User> findAddedBetween(Date start, Date end);
}
