package com.daar.boot;



import com.daar.adapter.out.jdbc.auth.JdbcCredential;
import com.daar.adapter.out.jdbc.auth.JdbcUser;
import com.daar.adapter.out.jdbc.auth.permission.JdbcPermission;
import com.daar.adapter.out.jdbc.auth.permission.JdbcRole;
import com.daar.adapter.out.jdbc.auth.permission.JdbcUseRole;
import com.daar.adapter.out.jdbc.auth.permission.JdbcUserPermission;
import com.daar.adapter.out.jdbc.document.JdbcDocument;
import com.daar.adapter.out.jdbc.document.JdbcType;
import com.daar.core.application.auth.CredentialService;
import com.daar.core.application.auth.UserService;
import com.daar.core.application.auth.permission.PermissionService;
import com.daar.core.application.auth.permission.RoleService;
import com.daar.core.application.document.DocumentService;
import com.daar.core.application.document.TypeService;

import javax.sql.DataSource;

public class AppContext {

    // --- Services ---
    public final UserService userService;
    public final CredentialService credentialService;
    public final PermissionService permissionService;
    public final RoleService roleService;
    public final DocumentService documentService;
    public final TypeService typeService;

    // --- Repositories (centralisés pour réutilisation) ---
    private final JdbcUser userRepo;
    private final JdbcCredential credentialRepo;
    private final JdbcPermission permissionRepo;
    private final JdbcUserPermission userPermissionRepo;
    private final JdbcRole roleRepo;
    private final JdbcUseRole useRoleRepo;
    private final JdbcDocument documentRepo;
    private final JdbcType typeRepo;

    public AppContext(DataSource ds) {

        // --- Création des repositories ---
        userRepo = new JdbcUser(ds);
        credentialRepo = new JdbcCredential(ds);
        permissionRepo = new JdbcPermission(ds);
        userPermissionRepo = new JdbcUserPermission(ds);
        roleRepo = new JdbcRole(ds);
        useRoleRepo = new JdbcUseRole(ds);
        documentRepo = new JdbcDocument(ds);
        typeRepo = new JdbcType(ds);

        // --- Initialisation des services ---
        userService = new UserService(userRepo);
        credentialService = new CredentialService(credentialRepo);
        permissionService = new PermissionService(permissionRepo, userPermissionRepo);
        roleService = new RoleService(roleRepo, useRoleRepo);
        documentService = new DocumentService(documentRepo);
        typeService = new TypeService(typeRepo);
    }
}
