package com.daar.core.port.in.usecase.auth.permission;

import com.daar.core.domain.model.auth.permission.Perm;
import com.daar.core.port.in.dto.permission.CreatePermissionDTO;
import com.daar.core.port.in.dto.permission.UpdatePermissionDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PermUseCase {

    Perm create(CreatePermissionDTO perm);
    Perm update(UUID permId, UpdatePermissionDTO perm);
    void delete(UUID id);
    Optional<Perm> getById(UUID id);
    List<Perm> allPermissions();





}
