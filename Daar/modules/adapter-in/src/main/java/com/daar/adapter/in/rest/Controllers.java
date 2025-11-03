package com.daar.adapter.in.rest;

import com.daar.adapter.in.rest.controller.AuthController;
import com.daar.adapter.in.rest.controller.UserController;
import io.javalin.Javalin;

public class Controllers {

    private final AuthController authController;
    private final UserController userController;

    public Controllers(AuthController authController, UserController userController) {
        this.authController = authController;
        this.userController = userController;
    }

    public void registerRoutes(Javalin app) {
        authController.registerRoutes(app);
        userController.registerRoutes(app);
    }
}
