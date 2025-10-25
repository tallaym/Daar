package com.daar.core.port.out.auth.permission;


import com.daar.core.domain.model.auth.permission.Perm;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PermRepository {
    Perm insert(String roleName, String description, UUID createdBy);
    Perm update(Perm permission);
    Optional<Perm> findById(UUID id);
    void delete(UUID id);




    List<Perm> allPermissions();


}