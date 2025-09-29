package com.daar.core.port.in.auth;

import com.daar.core.model.auth.Credential;

import java.util.Optional;
import java.util.UUID;

public interface CredentialUseCase {

    UUID createCredential(Credential mdp);
    Credential updateCredential(Credential mdp);

}
