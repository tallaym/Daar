package com.daar.core.application.service.auth.permission;

import com.daar.core.domain.model.auth.User;
import com.daar.core.domain.model.auth.permission.Perm;
import com.daar.core.domain.model.auth.permission.Role;
import com.daar.core.domain.model.auth.permission.UseRole;
import com.daar.core.port.in.dto.permission.CreateRoleDTO;
import com.daar.core.port.in.dto.permission.CreateUseRoleDTO;
import com.daar.core.port.in.dto.permission.UpdateRoleDTO;
import com.daar.core.port.in.dto.permission.UpdateUseRoleDTO;
import com.daar.core.port.in.usecase.auth.permission.RoleUseCase;
import com.daar.core.port.out.auth.permission.RoleRepository;
import com.daar.core.port.out.auth.permission.UseRoleRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class RoleService implements RoleUseCase {

    private final RoleRepository rr;
    private final UseRoleRepository urr;

    public RoleService(RoleRepository rr, UseRoleRepository urr) {
        this.rr = rr;
        this.urr = urr;
    }

    @Override
    public Role create(CreateRoleDTO dto) {
        return rr.insert(dto.getRoleName(), dto.getDescription(), dto.getCreatedBy());
    }

    @Override
    public Role update(UUID roleId, UpdateRoleDTO dto) {

        Role role = rr.findById(roleId).orElseThrow(() -> new RuntimeException("role not found"));

        role.setRoleName(dto.getRoleName());
        role.setDescription(dto.getDescription());
        role.setUpdatedAt(dto.getUpdatedAt());
        role.setUpdatedBy(dto.getUpdatedBy());



        return rr.update(role);
    }

    public void delete(UUID id) {
         rr.delete(id);
    }

    @Override
    public UseRole updateUR(UUID urId, UpdateUseRoleDTO dto) {
        UseRole ur = urr.findUseRole(urId).orElseThrow(() -> new RuntimeException("job not found"));

        ur.setUpdatedBy(dto.getUpdatedBy());
        ur.setUpdatedAt(dto.getUpdatedAt());
        ur.setExpiresAt(dto.getExpiresAt());


        return urr.update(ur);
    }

    @Override
    public UseRole assignToUser(CreateUseRoleDTO dto) {
        return urr.insert(dto.getUserId(), dto.getRoleId(), dto.getExpiresAt(),dto.getCreatedBy());
    }

    @Override
    public List<UseRole> useRoles(UUID userId) {
        return urr.findUserRoles(userId);
    }

    @Override
    public List<Role> allRoles() {
        return rr.allRoles();
    }
}
