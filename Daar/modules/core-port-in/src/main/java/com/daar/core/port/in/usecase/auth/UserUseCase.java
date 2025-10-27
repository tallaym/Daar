package com.daar.core.port.in.usecase.auth;


import com.daar.core.domain.model.auth.User;
import com.daar.core.port.in.dto.user.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserUseCase {

    UserDTO create(CreateUserCommand newUser);
    UserDTO modify(UUID userId, UpdateUserCommand userUpdated);
    List<UserDTO> listUsers();
    List<UserDTO> addedAfter(GetAfterDateQuery query);
    List<UserDTO> addedBetween(GetBetweenDateQuery query);
    Optional<UserDTO> getUserById(GetUserQuery query);


}
