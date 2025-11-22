package com.daar.boot;

import com.daar.adapter.in.rest.auth.controller.AuthController;
import com.daar.adapter.in.rest.auth.controller.UserController;
import com.daar.adapter.out.config.DB;
import com.daar.adapter.out.config.AppDataSourceProvider;
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

        DataSource ds = new AppDataSourceProvider(dbconfig).createDataSource();

        AppContext appContext = new AppContext(ds);

        Javalin app = Javalin.create().start(7000);
        app.get("/", ctx -> ctx.result("Marhaba fi Daar API"));
            new UserController(appContext.userUseCaseImplementation).registerRoutes(app);
            new AuthController(appContext.authUseCaseImplementation).registerRoutes(app);

        System.out.println("Server started on port 7000");
    }
}
