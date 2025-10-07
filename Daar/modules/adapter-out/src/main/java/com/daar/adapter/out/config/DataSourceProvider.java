package com.daar.adapter.out.config;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class DataSourceProvider {

    private final DB config;

    public DataSourceProvider(DB config) {
        this.config = config;
    }



    public DataSource createDataSource() {
        switch (config.getDriver()) {


            case "org.postgresql.Driver":
                PGSimpleDataSource pg = new PGSimpleDataSource();
                pg.setURL(config.getUrl());
                pg.setUser(config.getUser());
                pg.setPassword(config.getPassword());
                return pg;

            default:
                throw new IllegalArgumentException("Driver non supporté : " + config.getDriver());
        }
    }

}
