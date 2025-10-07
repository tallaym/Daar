package com.daar.core.port.in.auth.permission;

import com.daar.core.domain.model.auth.User;
import com.daar.core.domain.model.auth.permission.Perm;
import com.daar.core.domain.model.auth.permission.UserPermission;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PermUseCase {

    Perm create(Perm perm);
    Perm update(Perm perm);
    void delete(UUID id);
    Optional<Perm> getById(UUID id);
    List<Perm> allPermissions();

    UserPermission updateUP(UserPermission up);
    UserPermission assignToUser(User u, Perm p, Instant end, User agent);


    List<UserPermission> userPermissions(UUID userId);



}
