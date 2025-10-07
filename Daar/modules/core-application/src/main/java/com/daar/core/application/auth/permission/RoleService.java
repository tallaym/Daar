package com.daar.core.application.auth.permission;

import com.daar.core.domain.model.auth.User;
import com.daar.core.domain.model.auth.permission.Role;
import com.daar.core.domain.model.auth.permission.UseRole;
import com.daar.core.port.in.auth.permission.RoleUseCase;
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
    public Role create(Role r) {
        return rr.insert(r);
    }

    @Override
    public Role update(Role r) {
        return rr.update(r);
    }

    public void delete(UUID id) {
         rr.delete(id);
    }

    @Override
    public UseRole updateUR(UseRole ur) {
        return urr.update(ur);
    }

    @Override
    public UseRole assignToUser(User u, Role r, Instant end, User agent) {
        return urr.insert(u,r,end,agent);
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
