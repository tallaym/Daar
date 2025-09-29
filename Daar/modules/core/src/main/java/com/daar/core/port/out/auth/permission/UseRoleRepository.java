package com.daar.core.port.out.auth.permission;

import com.daar.core.model.auth.permission.UseRole;
import com.daar.core.model.auth.permission.UserPermission;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UseRoleRepository {

    UseRole insert(UseRole ur);
    UseRole update(UseRole ur);

    List<UseRole> findUserRoles(UUID userId);
    List<UseRole> findRoleUsers(UUID roleId);
    List<UseRole> findAll();
    Optional<UseRole> findUseRole(UUID userId, UUID roleId);
}
