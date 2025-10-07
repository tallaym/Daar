package com.daar.core.port.in.auth.permission;


import com.daar.core.domain.model.auth.User;
import com.daar.core.domain.model.auth.permission.Role;
import com.daar.core.domain.model.auth.permission.UseRole;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface RoleUseCase {

    Role create(Role r);
    Role update(Role r);


    UseRole updateUR(UseRole ur);

    UseRole assignToUser(User u, Role r, Instant end, User agent);

    List<UseRole> useRoles(UUID userId);

    List<Role> allRoles();
}
