package com.daar.core.port.out.auth.permission;

import com.daar.core.model.auth.permission.UserPermission;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserPermissionRepository {
    UserPermission save(UserPermission up);
    boolean deleteById(UUID id);
    List<UserPermission> findUserPermissions(UUID userId);
    List<UserPermission> findPermissionUses(UUID permissionId);
    List<UserPermission> findAll();
    Optional<UserPermission> findUserPermission(UUID userId, UUID permissionId);
}
