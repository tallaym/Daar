package com.daar.core.port.in.auth;

import com.daar.core.model.auth.Credential;
import com.daar.core.model.auth.User;
import com.daar.core.model.auth.permission.UseRole;
import com.daar.core.model.auth.permission.UserPermission;
import com.daar.core.model.document.Document;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserUseCase {

    UUID createUser(User u);
    User updateUser(UUID id, User u);
    List<User> listUsers();
    List<User> addedAfter(Date start);
    List<User> addedBetween(Date start, Date end);
    Optional<User> getUserById(UUID id);

    User activate(User u);


    List<Document> documentsAddedBy(UUID id);
    List<Document> documentsLinkedTo(UUID id);
    List<Credential> listCredentials(UUID id);
    List<UserPermission> usersPermissions();
    List<UseRole> usersRoles();

}
