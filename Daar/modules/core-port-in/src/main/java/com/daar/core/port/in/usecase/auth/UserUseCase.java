package com.daar.core.port.in.usecase.auth;


import com.daar.core.port.in.dto.auth.CreateUserCommand;
import com.daar.core.port.in.dto.auth.UpdateUserCommand;
import com.daar.core.port.in.dto.auth.user.UserDTO;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserUseCase {

    UserDTO createUser(CreateUserCommand command);
    UserDTO updateUser(UpdateUserCommand command);
    List<UserDTO> listUsers();
    List<UserDTO> addedAfter(Date query);
    List<UserDTO> addedBetween(Date start, Date end);
    Optional<UserDTO> getUserById(UUID query);


}
