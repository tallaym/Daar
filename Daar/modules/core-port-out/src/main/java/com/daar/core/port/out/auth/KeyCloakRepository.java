package com.daar.core.port.out.auth;

import java.util.UUID;

public interface KeyCloakRepository {

    String createUser(String firstname, String lastname, String phone);
    boolean updateUser(String keyCloakId, String firstname, String lastname, String phone);
    boolean deleteUser(String keyCloakId);

    boolean changePassword(String keyCloakId, String newPassword);
    boolean resetPassword(String contact);


}
