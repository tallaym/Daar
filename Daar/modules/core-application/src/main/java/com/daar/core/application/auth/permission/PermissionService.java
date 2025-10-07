package com.daar.core.application.auth.permission;


import com.daar.core.domain.model.auth.User;
import com.daar.core.domain.model.auth.permission.Perm;
import com.daar.core.domain.model.auth.permission.UserPermission;
import com.daar.core.port.in.auth.permission.PermUseCase;
import com.daar.core.port.out.auth.permission.PermRepository;
import com.daar.core.port.out.auth.permission.UserPermRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PermissionService implements PermUseCase {

    private final PermRepository pr;
    private final UserPermRepository upr;

    public PermissionService(PermRepository pr, UserPermRepository upr) {
        this.pr = pr;
        this.upr = upr;
    }

    @Override
    public Perm create(Perm perm) {
        return pr.insert(perm);
    }

    @Override
    public Perm update(Perm perm) {
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

    @Override
    public UserPermission updateUP(UserPermission up) {
        return upr.update(up);
    }
    @Override
    public UserPermission assignToUser(User u, Perm p, Instant end, User agent) {
       return upr.insert(u, p, end, agent);
    }


    @Override
    public List<UserPermission> userPermissions(UUID userId) {
        return upr.findUserPermissions(userId);
    }
}
