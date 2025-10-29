module com.daar.adapter.out {
    requires com.daar.core.domain;
    requires java.sql;
    requires com.daar.core.port.out;

    requires keycloak.admin.client;
    requires org.postgresql.jdbc;
    requires keycloak.client.common.synced;


    exports com.daar.adapter.out.jdbc.document;
    exports com.daar.adapter.out.jdbc;
}
