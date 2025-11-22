package com.daar.adapter.out.jdbc;

import com.daar.adapter.out.config.AppDataSourceProvider;
import com.daar.adapter.out.config.DB;
import com.daar.core.domain.model.auth.User;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JdbcUserTest {

    static DataSource dataSource;
    JdbcUser jdbcUser;

    @BeforeAll
    static void setupDatabase() throws Exception {
        dataSource = new AppDataSourceProvider(DB.defaultH2()).createDataSource();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE users(
                    id UUID PRIMARY KEY,
                    firstname VARCHAR(50),
                    lastname VARCHAR(50),
                    phone VARCHAR(20),
                    keyCloakId VARCHAR(50),
                    origin VARCHAR(50),
                    identityType VARCHAR(20),
                    identityNumber VARCHAR(50),
                    address VARCHAR(100),
                    email VARCHAR(50),
                    createdAt TIMESTAMP,
                    updatedAt TIMESTAMP,
                    suspendedUntil TIMESTAMP,
                    createdBy UUID,
                    updatedBy UUID,
                    suspendedBy UUID
                )
                """);
        }
    }

    @BeforeEach
    void init() {
        jdbcUser = new JdbcUser(dataSource);
    }

    @Test
    void testInsertAndFindById() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setPhone("123456789");
        user.setKeycloakId("kc-123");
        user.setCreatedBy(UUID.randomUUID());

        User inserted = jdbcUser.insert(user);
        assertNotNull(inserted.getId());

        Optional<User> fetched = jdbcUser.findById(user.getId());
        assertTrue(fetched.isPresent());
        assertEquals("John", fetched.get().getFirstname());
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstname("Jane");
        user.setLastname("Smith");
        user.setPhone("987654321");
        user.setKeycloakId("kc-987");
        user.setCreatedBy(UUID.randomUUID());
        jdbcUser.insert(user);

        user.setFirstname("Janet");
        user.setPhone("111222333");
        User updated = jdbcUser.update(user);

        assertEquals("Janet", updated.getFirstname());
        Optional<User> fetched = jdbcUser.findById(user.getId());
        assertTrue(fetched.isPresent());
        assertEquals("111222333", fetched.get().getPhone());
    }

    @Test
    void testFindAll() {
        jdbcUser.insert(new User(UUID.randomUUID(), null, "A", "B", null, null, null, null, null, null, Instant.now(), null, null, UUID.randomUUID(), null, null));
        jdbcUser.insert(new User(UUID.randomUUID(), null, "C", "D", null, null, null, null, null, null, Instant.now(), null, null, UUID.randomUUID(), null, null));

        List<User> users = jdbcUser.findAll();
        assertTrue(users.size() >= 2);
    }

}
