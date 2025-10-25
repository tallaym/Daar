package com.daar.core.port.in.usecase.auth;


import com.daar.core.domain.model.auth.Credential;
import com.daar.core.port.in.dto.credential.CreateCredentialDTO;
import com.daar.core.port.in.dto.credential.UpdateCredentialDTO;

import java.util.List;
import java.util.UUID;

public interface CredentialUseCase {

    UUID create(CreateCredentialDTO newCredential);
    Credential modify(String identifier, UpdateCredentialDTO updatedCredential);

    List<Credential> userCredentials(UUID id);

}
