module com.daar.core.port.in {
    requires com.daar.core.domain;

    exports com.daar.core.port.in.usecase.auth.permission;
    exports com.daar.core.port.in.usecase.document;
    exports com.daar.core.port.in.usecase.auth;
    exports com.daar.core.port.in.usecase.auth.activity;
    exports com.daar.core.port.in.dto.login;
    exports com.daar.core.port.in.dto.credential;

    exports com.daar.core.port.in.dto.user;
    exports com.daar.core.port.in.dto.permission;


}
