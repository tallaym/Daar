package com.daar.core.port.out.auth.permission;


import com.daar.core.domain.model.auth.User;
import com.daar.core.domain.model.auth.permission.Role;
import com.daar.core.domain.model.auth.permission.UseRole;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UseRoleRepository {

    UseRole insert(User u, Role r, Instant end, User agent);
    UseRole update(UseRole ur);
    void delete(UUID userId, UUID roleId);
    List<UseRole> findUserRoles(UUID userId);
    List<UseRole> findRoleUsers(UUID roleId);
    List<UseRole> findAll();
    Optional<UseRole> findUseRole(UUID userId, UUID roleId);
}
