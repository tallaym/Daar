module com.daar.adapter.out {
    requires com.daar.core.domain;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires com.daar.core;


    exports com.daar.adapter.out.jdbc.auth.permission;
   exports com.daar.adapter.out.jdbc.auth;
   exports com.daar.adapter.out.jdbc.document;
}
