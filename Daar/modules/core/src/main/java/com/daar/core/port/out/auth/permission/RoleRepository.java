package com.daar.core.port.out.auth.permission;

import com.daar.core.model.auth.User;
import com.daar.core.model.auth.permission.Permission;
import com.daar.core.model.auth.permission.Role;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository {
    Role save(Role role);
    Optional<Role> findById(UUID id);
    boolean deleteById(UUID id);
    List<Role> allRoles();
    List<Permission> getAllPermissions(UUID roleId);

}
