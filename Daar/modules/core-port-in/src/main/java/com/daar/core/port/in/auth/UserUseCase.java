package com.daar.core.port.in.auth;


import com.daar.core.domain.model.auth.User;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserUseCase {

    UUID create(String firstname, String lastname, String phone, UUID createdBy);
    User modify(User u);
    List<User> listUsers();
    List<User> addedAfter(Date start);
    List<User> addedBetween(Date start, Date end);
    Optional<User> getUserById(UUID id);


}
