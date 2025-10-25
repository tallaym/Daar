module com.daar.core.application {

    requires com.daar.core.domain;
    requires com.daar.core.port.in;
    requires com.daar.core.port.out;
    requires jbcrypt;


    exports com.daar.core.application.service.auth;

    exports com.daar.core.application.service.auth.permission;
    exports com.daar.core.application.service.document;



}
