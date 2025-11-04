module com.daar.adapter.in {
    exports com.daar.adapter.in.rest.controller;
    requires java.sql;
    requires io.javalin;


requires com.daar.core.domain;
requires com.daar.core.port.in;

}
