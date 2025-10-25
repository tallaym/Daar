module com.daar.adapter.out {
    requires com.daar.core.domain;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires com.daar.core;

    requires spring.security.crypto;
    requires jjwt.api;
    requires com.daar.core.port.out;


    exports com.daar.adapter.out.jdbc.auth.permission;
   exports com.daar.adapter.out.jdbc.auth;
   exports com.daar.adapter.out.jdbc.document;
}
