package com.daar.core.port.out.auth.permission;


import com.daar.core.domain.model.auth.permission.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository {
    Role insert(Role role);
    Role update(Role role);
    void delete(UUID id);
    Optional<Role> findById(UUID id);
    List<Role> allRoles();

}
