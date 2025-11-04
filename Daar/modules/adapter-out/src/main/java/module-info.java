module com.daar.adapter.out {
    requires com.daar.core.domain;
    requires java.sql;
    requires com.daar.core.port.out;
    requires com.zaxxer.hikari;

    requires org.postgresql.jdbc;

    requires jakarta.ws.rs;
    requires keycloak.admin.client;
    requires keycloak.client.common.synced;


    exports com.daar.adapter.out.jdbc.document;
    exports com.daar.adapter.out.jdbc;
    exports com.daar.adapter.out.config;
}
