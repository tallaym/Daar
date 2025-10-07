package com.daar.core.application.auth;

import com.daar.core.domain.model.auth.User;
import com.daar.core.port.out.auth.CredentialRepository;
import com.daar.core.port.out.auth.UserRepository;

public class LoginService {

    private final CredentialRepository cr;
    private final UserRepository ur;

    private final Password passwordHasher

    public LoginService(CredentialRepository cr, UserRepository ur) {
        this.cr = cr;
        this.ur = ur;
    }

    public User login()
}
