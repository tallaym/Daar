package com.daar.adapter.out.config;

public class DB {

    private final String url, user, password, driver;

    public DB(String url, String user, String password, String driver) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.driver = driver;
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

    public static DB defaultH2(){
        return new DB(
                "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
                "sa",
                "",
                "org.h2.Driver"
        );
    }

    public static DB postgre(String host, int port, String db, String user, String mdp){
        return new DB(
                "jdbc:postgresql://" + host + ":" + port + "/" + db,
                user,
                mdp,
                "org.postgresql.Driver"
        );
    }
}
