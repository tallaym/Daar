package com.daar.core.domain.repository.auth;

import com.daar.core.domain.repository.KeycloakTokenDTO;

public interface KeyCloakRepository {

    String createKeycloakUser(String firstname, String lastname, String phone);
    boolean updateKeycloakUser(String keyCloakId, String firstname, String lastname, String phone, String email);
    boolean deleteKeycloakUser(String keyCloakId);

    boolean changePassword(String keyCloakId, String newPassword);
    boolean resetPassword(String contact);

    KeycloakTokenDTO login(String identifier, String secret);
    boolean logout(String keyCloakId);
    KeycloakTokenDTO refreshToken(String refreshToken);





}
