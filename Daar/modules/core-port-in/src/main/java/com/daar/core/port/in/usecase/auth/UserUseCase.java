package com.daar.core.port.in.usecase.auth;


import com.daar.core.port.in.dto.user.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserUseCase {

    UserDTO create(CreateUserCommand newUser);
    UserDTO modify(UUID userId, UpdateUserCommand userUpdated);
    List<UserDTO> listUsers();
    List<UserDTO> addedAfter(Date query);
    List<UserDTO> addedBetween(Date start, Date end);
    Optional<UserDTO> getUserById(UUID query);


}
