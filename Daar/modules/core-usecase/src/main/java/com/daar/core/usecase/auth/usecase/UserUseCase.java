package com.daar.core.usecase.auth.usecase;


import com.daar.core.usecase.auth.command.CreateUserCommand;
import com.daar.core.usecase.auth.command.UpdateUserCommand;
import com.daar.core.usecase.auth.dto.UserDTO;


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
