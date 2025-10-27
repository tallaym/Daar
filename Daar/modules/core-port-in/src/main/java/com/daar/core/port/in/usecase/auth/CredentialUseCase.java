package com.daar.core.port.in.usecase.auth;


import com.daar.core.port.in.dto.credential.CreateCredentialCommand;
import com.daar.core.port.in.dto.credential.CredentialDTO;
import com.daar.core.port.in.dto.credential.GetCredentialQuery;
import com.daar.core.port.in.dto.credential.UpdateCredentialCommand;

import java.util.List;

public interface CredentialUseCase {

    CredentialDTO create(CreateCredentialCommand newCredential);
    CredentialDTO modify(String identifier, UpdateCredentialCommand updatedCredential);

    List<CredentialDTO> userCredentials(GetCredentialQuery query);

}
