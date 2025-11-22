package com.daar.adapter.out.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class AppDataSourceProvider {

    private final DB config;

    public AppDataSourceProvider(DB config) {
        this.config = config;
    }



    public DataSource createDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(config.getUrl());
            hikariConfig.setUsername(config.getUser());
            hikariConfig.setPassword(config.getPassword());
            hikariConfig.setDriverClassName(config.getDriver());
            hikariConfig.setMaximumPoolSize(10);
            hikariConfig.setMinimumIdle(2);
            hikariConfig.setIdleTimeout(30000);
            hikariConfig.setConnectionTimeout(30000);

            return new HikariDataSource(hikariConfig);
    }

}
