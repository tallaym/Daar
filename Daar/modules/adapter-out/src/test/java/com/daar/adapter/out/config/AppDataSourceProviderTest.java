package com.daar.adapter.out.config;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class AppDataSourceProviderTest {
    private void runTestWithDataSource(DB dbConfig) throws Exception {
        DataSource ds = new AppDataSourceProvider(dbConfig).createDataSource();

        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement()) {

            // Création de table
            stmt.execute("CREATE TABLE test(id INT PRIMARY KEY, name VARCHAR(50))");

            // Insertion
            stmt.execute("INSERT INTO test(id, name) VALUES (1, 'Alice')");

            // Vérification
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM test");
            rs.next();
            assertEquals(1, rs.getInt(1));
        }
    }

    @Test
    void h2Test() throws Exception {
        runTestWithDataSource(DB.defaultH2());
    }

    @Test
    void postgresTest() throws Exception {
        try (PostgreSQLContainer<?> pg = new PostgreSQLContainer<>("postgres:15")) {
            pg.start();

            DB pgConfig = DB.postgre(
                    pg.getHost(),
                    pg.getFirstMappedPort(),
                    pg.getDatabaseName(),
                    pg.getUsername(),
                    pg.getPassword()
            );

            runTestWithDataSource(pgConfig);
        }
    }

}