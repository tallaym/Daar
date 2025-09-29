package com.daar.core.port.in.auth.permission;

import com.daar.core.model.auth.User;
import com.daar.core.model.auth.permission.Permission;
import com.daar.core.model.auth.permission.Role;
import com.daar.core.model.auth.permission.UserPermission;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface PermissionUseCase {

    UUID createPermission(Permission perm);
    Permission updatePermission(Permission perm);
    Permission deletePermission(UUID id);
    List<Permission> allPermissions();

    UserPermission changeTimeOut(User u, Permission perm, Date newdate);
    UserPermission assignToUser(User u, Permission perm);
    Permission assignToRole(UUID roleId, Permission perm);



}
