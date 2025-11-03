package com.daar.adapter.in.rest;


import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;


public class RestServer {

    private final Controllers controllers;
    private Javalin app;
    private final int port;

    public RestServer(Controllers controllers, int port) {
        this.controllers = controllers;
        this.port = port;
    }


    public void start() {
        app = Javalin.create()
                .before(ctx -> ctx.contentType("application/json"))
                .before(ctx -> {
                    ctx.header("Access-Control-Allow-Origin", "*");
                    ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                    ctx.header("Access-Control-Allow-Headers", "*");

            }).start(port);

        controllers.registerRoutes(app);

        System.out.println("REST server started on port " + port);

    }



    public void stop() {
        if (app != null) {
            app.stop();
            System.out.println("REST server stopped.");
        }
    }

    public Javalin getApp() {
        return app;
    }
}
