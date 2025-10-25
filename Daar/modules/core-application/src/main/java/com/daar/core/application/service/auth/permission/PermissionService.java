package com.daar.core.application.service.auth.permission;


import com.daar.core.domain.model.auth.permission.Perm;
import com.daar.core.port.in.dto.credential.CreateCredentialDTO;
import com.daar.core.port.in.dto.credential.UpdateCredentialDTO;
import com.daar.core.port.in.dto.permission.CreatePermissionDTO;
import com.daar.core.port.in.dto.permission.UpdatePermissionDTO;
import com.daar.core.port.in.usecase.auth.permission.PermUseCase;
import com.daar.core.port.out.auth.permission.PermRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PermissionService implements PermUseCase {

    private final PermRepository pr;

    public PermissionService(PermRepository pr) {
        this.pr = pr;
    }

    @Override
    public Perm create(CreatePermissionDTO dto) {
        return pr.insert(dto.getPermission_name(), dto.getDescription(), dto.getCreatedBy());
    }

    @Override
    public Perm update(UUID permId, UpdatePermissionDTO dto) {
        Perm perm = pr.findById(permId).orElseThrow(() -> new RuntimeException("permission not found"));

        perm.setPermission_name(dto.getPermission_name());
        perm.setDescription(dto.getDescription());
        perm.setUpdatedAt(dto.getUpdatedAt());
        perm.setUpdatedBy(dto.getUpdatedBy());
        perm.setRoleId(dto.getRoleId());

        return pr.update(perm);
    }

    @Override
    public void delete(UUID id) {
        pr.delete(id);
    }

    @Override
    public Optional<Perm> getById(UUID id) {
        return pr.findById(id);
    }

    @Override
    public List<Perm> allPermissions() {
        return pr.allPermissions();
    }

}
