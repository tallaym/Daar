package com.daar.boot;

import com.daar.adapter.out.jdbc.JdbcKeyCloak;
import com.daar.adapter.out.jdbc.JdbcUser;
import com.daar.adapter.out.jdbc.KeyCloakConfig;
import com.daar.adapter.out.jdbc.document.JdbcDocument;
import com.daar.adapter.out.jdbc.document.JdbcType;
import com.daar.core.usecase.auth.usecase.impl.AuthUseCaseImplementation;
import com.daar.core.usecase.auth.usecase.impl.UserUseCaseImplementation;
import com.daar.core.usecase.document.usecase.impl.DocumentService;
import com.daar.core.usecase.document.usecase.impl.TypeService;
import com.daar.core.domain.service.UserManagement;

import javax.sql.DataSource;

public class AppContext {

    // --- Services ---
    public final UserUseCaseImplementation userUseCaseImplementation;
    public final AuthUseCaseImplementation authUseCaseImplementation;
    public final DocumentService documentService;
    public final TypeService typeService;

    // --- Repositories ---
    private final JdbcUser userRepo;
    private final JdbcKeyCloak keyCloakRepo;
    private final JdbcDocument documentRepo;
    private final JdbcType typeRepo;

    public AppContext(DataSource ds) {

        // --- Création des repositories ---
        userRepo = new JdbcUser(ds);

        // --- Lecture variables d'environnement ou valeurs par défaut ---
        String baseUrl      =  "http://localhost:8080/";
        String realm        = "daar-realm";
        String clientId     = "daar-client";
        String secret       = "client-secret";
        String adminUser    = "admin";
        String adminPassword=  "admin-password";

        KeyCloakConfig config = new KeyCloakConfig(
                baseUrl,
                realm,
                clientId,
                secret,
                adminUser,
                adminPassword
        );

        keyCloakRepo = new JdbcKeyCloak(config);
        documentRepo = new JdbcDocument(ds);
        typeRepo = new JdbcType(ds);

        UserManagement userManagement = new UserManagement(userRepo, keyCloakRepo);

        // --- Initialisation des services ---
        userUseCaseImplementation = new UserUseCaseImplementation(userRepo, userManagement);
        authUseCaseImplementation = new AuthUseCaseImplementation(keyCloakRepo);
        documentService = new DocumentService(documentRepo);
        typeService = new TypeService(typeRepo);
    }

}
