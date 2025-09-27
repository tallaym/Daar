package com.daar.core.port.out.auth.permission;

import com.daar.core.model.auth.User;
import com.daar.core.model.auth.permission.Permission;
import com.daar.core.model.auth.permission.Role;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository {
    Permission save(Permission permission);
    Optional<Permission> findById(UUID id);
    boolean deleteById(UUID id);
    List<Permission> allPermissions();


}