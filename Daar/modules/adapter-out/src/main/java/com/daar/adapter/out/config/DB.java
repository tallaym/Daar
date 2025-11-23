package com.daar.adapter.out.config;

public class DB {

    private final String url, user, password, driver;
    private final int poolSize, minIdle;

    private final long idleTimeout, connectionTimeout;

    public DB(String url, String user, String password, String driver, int poolSize, int minIdle, long idleTimeout, long connectionTimeout) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.driver = driver;
        this.poolSize = poolSize;
        this.minIdle = minIdle;
        this.idleTimeout = idleTimeout;
        this.connectionTimeout = connectionTimeout;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDriver() {
        return driver;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public long getIdleTimeout() {
        return idleTimeout;
    }

    public long getConnectionTimeout() {
        return connectionTimeout;
    }

    public static DB defaultH2(){
        return new DB(
                "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
                "sa",
                "",
                "org.h2.Driver",
                10,
                2,
                30000,
                30000
        );
    }

    public static DB postgre(String host, int port, String db, String user, String mdp){
        return new DB(
                "jdbc:postgresql://" + host + ":" + port + "/" + db,
                user,
                mdp,
                "org.postgresql.Driver",
                10,
                2,
                30000,
                30000
        );
    }
}
