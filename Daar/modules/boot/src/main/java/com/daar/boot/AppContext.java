package com.daar.boot;

import com.daar.adapter.out.jdbc.JdbcKeyCloak;
import com.daar.adapter.out.jdbc.JdbcUser;
import com.daar.adapter.out.jdbc.KeyCloakConfig;
import com.daar.adapter.out.jdbc.document.JdbcDocument;
import com.daar.adapter.out.jdbc.document.JdbcType;
import com.daar.core.application.service.auth.AuthService;
import com.daar.core.application.service.auth.UserService;
import com.daar.core.application.service.document.DocumentService;
import com.daar.core.application.service.document.TypeService;

import javax.sql.DataSource;

public class AppContext {

    // --- Services ---
    public final UserService userService;
    public final AuthService authService;
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

        // --- Initialisation des services ---
        userService = new UserService(userRepo);
        authService = new AuthService(keyCloakRepo);
        documentService = new DocumentService(documentRepo);
        typeService = new TypeService(typeRepo);
    }

}
