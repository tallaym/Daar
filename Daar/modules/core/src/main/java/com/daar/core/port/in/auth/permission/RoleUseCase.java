package com.daar.core.port.in.auth.permission;

import com.daar.core.model.auth.User;
import com.daar.core.model.auth.permission.Permission;
import com.daar.core.model.auth.permission.Role;
import com.daar.core.model.auth.permission.UseRole;
import com.daar.core.model.auth.permission.UserPermission;

import java.util.Date;
import java.util.UUID;

public interface RoleUseCase {

    UUID createRole(Role r);
    Role updateRole(Role r);
    Role deleteRole(UUID id);


    UseRole changeTimeOut(User u, Role r, Date newdate);
    UseRole assignToUser(User u, Role r);
}
