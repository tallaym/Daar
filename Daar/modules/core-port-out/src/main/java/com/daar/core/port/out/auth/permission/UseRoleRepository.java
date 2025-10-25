package com.daar.core.port.out.auth.permission;


import com.daar.core.domain.model.auth.permission.UseRole;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UseRoleRepository {

    UseRole insert(UUID userID, UUID roleId, Instant end, UUID agentId);
    UseRole update(UseRole ur);
    void delete(UUID userId, UUID roleId);
    List<UseRole> findUserRoles(UUID userId);

    Optional<UseRole> findByUserId(UUID userId);
    List<UseRole> findRoleUsers(UUID roleId);
    List<UseRole> findAll();
    Optional<UseRole> findUseRole(UUID urId);
}
