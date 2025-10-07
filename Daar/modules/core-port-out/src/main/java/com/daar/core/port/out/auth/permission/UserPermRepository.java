package com.daar.core.port.out.auth.permission;


import com.daar.core.domain.model.auth.User;
import com.daar.core.domain.model.auth.permission.Perm;
import com.daar.core.domain.model.auth.permission.UserPermission;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserPermRepository {
    UserPermission insert(User u, Perm p, Instant expiration, User agent);
    UserPermission update(UserPermission up);

    void delete(UUID userId, UUID permissionId);
    List<UserPermission> findUserPermissions(UUID userId);
    List<UserPermission> findPermissionUses(UUID permissionId);
    List<UserPermission> findAll();
    Optional<UserPermission> findUserPermission(UUID userId, UUID permissionId);
}
