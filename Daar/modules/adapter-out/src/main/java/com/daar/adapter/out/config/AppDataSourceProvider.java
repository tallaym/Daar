package com.daar.adapter.out.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
public class AppDataSourceProvider {

    private final DB config;
    private volatile DataSource dataSource;

    public AppDataSourceProvider(DB config) {
        this.config = config;
    }

    public AppDataSourceProvider(DataSource ds) { // pour les tests
        this.config = null;
        this.dataSource = ds;
    }

    public DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (this) {
                if (dataSource == null) {
                    dataSource = createHikariDataSource();
                }
            }
        }
        return dataSource;
    }

    private HikariDataSource createHikariDataSource() {
        if (config == null) throw new IllegalStateException("DB config manquante");
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getUrl());
        hikariConfig.setUsername(config.getUser());
        hikariConfig.setPassword(config.getPassword());
        hikariConfig.setDriverClassName(config.getDriver());
        hikariConfig.setMaximumPoolSize(config.getPoolSize());
        hikariConfig.setMinimumIdle(config.getMinIdle());
        hikariConfig.setIdleTimeout(config.getIdleTimeout());
        hikariConfig.setConnectionTimeout(config.getConnectionTimeout());
        try {
            return new HikariDataSource(hikariConfig);
        } catch (Exception e) {
            throw new IllegalStateException("Impossible de créer le DataSource", e);
        }
    }
}


