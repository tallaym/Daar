package com.daar.adapter.out.jdbc;

import com.daar.adapter.out.config.AppDataSourceProvider;
import com.daar.adapter.out.config.DB;
import com.daar.core.domain.model.auth.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JdbcUserTest {

    private JdbcUser userRepo;

    @BeforeEach
    void setup() throws Exception {
        DB h2Config = new DB("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL", "sa", "", "org.h2.Driver", 10, 2, 30000, 30000);
        AppDataSourceProvider provider = new AppDataSourceProvider(h2Config);
        DataSource ds = provider.getDataSource();

        try (Connection conn = ds.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS users");
            stmt.execute("CREATE TABLE users (" +
                    "id VARCHAR(36) PRIMARY KEY," +
                    "firstname VARCHAR(50)," +
                    "lastname VARCHAR(50)," +
                    "phone VARCHAR(20)," +
                    "keyCloakId VARCHAR(50)," +
                    "createdBy VARCHAR(36)," +
                    "updatedBy VARCHAR(36)," +
                    "suspendedBy VARCHAR(36)," +
                    "origin VARCHAR(50)," +
                    "identityType VARCHAR(20)," +
                    "identityNumber VARCHAR(50)," +
                    "address VARCHAR(100)," +
                    "email VARCHAR(50)," +
                    "createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "updatedAt TIMESTAMP," +
                    "suspendedUntil TIMESTAMP" +
                    ");");
        }

        userRepo = new JdbcUser(ds);
    }

    @Test
    void insertAndFindUser() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstname("Alice");
        user.setLastname("Dupont");
        user.setPhone("123456789");
        user.setCreatedBy(UUID.randomUUID());
        user.setKeycloakId("kc-1");


        userRepo.insert(user);

        User found = userRepo.findById(user.getId()).orElseThrow();
        assertEquals("Alice", found.getFirstname());
        assertEquals("123456789", found.getPhone());

        List<User> allUsers = userRepo.findAll();
        assertEquals(1, allUsers.size());
    }

    @Test
    void updateUser() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstname("Bob");
        user.setLastname("Martin");
        user.setPhone("987654321");
        user.setCreatedBy(UUID.randomUUID());
        user.setKeycloakId("kc-2");

        userRepo.insert(user);

        user.setFirstname("Bobby");
        userRepo.update(user);

        User updated = userRepo.findById(user.getId()).orElseThrow();
        assertEquals("Bobby", updated.getFirstname());
    }

   }
