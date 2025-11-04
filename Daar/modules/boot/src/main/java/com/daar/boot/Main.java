package com.daar.boot;

import com.daar.adapter.in.rest.controller.AuthController;
import com.daar.adapter.in.rest.controller.UserController;
import com.daar.adapter.out.config.DB;
import com.daar.adapter.out.config.DataSourceProvider;
import io.javalin.Javalin;

import javax.sql.DataSource;

public class Main {

    public static void main(String[] args) {

        DB dbconfig = new DB(
                "jdbc:postgresql://localhost:5432/users",
                "sidi",
                "marhaba",
                "org.postgresql.Driver"
        );

        DataSource ds = new DataSourceProvider(dbconfig).createDataSource();

        AppContext appContext = new AppContext(ds);

        Javalin app = Javalin.create().start(7000);
            new UserController(appContext.userService, appContext.authService).registerRoutes(app);
            new AuthController(appContext.authService).registerRoutes(app);

        System.out.println("Server started on port 7000");
    }
}
