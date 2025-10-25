package com.daar.core.port.in.usecase.auth;


import com.daar.core.domain.model.auth.User;
import com.daar.core.port.in.dto.user.CreateUserDTO;
import com.daar.core.port.in.dto.user.UpdateUserDTO;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserUseCase {

    UUID create(CreateUserDTO newUser);
    User modify(UUID userId, UpdateUserDTO userUpdated);
    List<User> listUsers();
    List<User> addedAfter(Date start);
    List<User> addedBetween(Date start, Date end);
    Optional<User> getUserById(UUID id);


}
