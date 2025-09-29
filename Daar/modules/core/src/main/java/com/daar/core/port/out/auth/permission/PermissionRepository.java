package com.daar.core.port.out.auth.permission;

import com.daar.core.model.auth.User;
import com.daar.core.model.auth.permission.Permission;
import com.daar.core.model.auth.permission.Role;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository {
    Permission insert(Permission permission);
    Permission update(Permission permission);
    Optional<Permission> findById(UUID id);


    List<Permission> allPermissions();


}