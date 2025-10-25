package com.daar.core.port.in.usecase.auth.permission;


import com.daar.core.domain.model.auth.permission.Role;
import com.daar.core.domain.model.auth.permission.UseRole;
import com.daar.core.port.in.dto.permission.*;

import java.util.List;
import java.util.UUID;

public interface RoleUseCase {

    Role create(CreateRoleDTO newRole);
    Role update(UUID roleId, UpdateRoleDTO roleUpdated);

    UseRole updateUR(UUID urId, UpdateUseRoleDTO jobUpdated);

    UseRole assignToUser(CreateUseRoleDTO newJob);

    List<UseRole> useRoles(UUID userId);

    List<Role> allRoles();
}
