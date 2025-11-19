package com.daar.core.domain.service;

import com.daar.core.domain.model.auth.User;
import com.daar.core.domain.port_out.auth.KeyCloakRepository;
import com.daar.core.domain.port_out.auth.UserRepository;

public class UserManagement {

    private final UserRepository userRepository;
    private final KeyCloakRepository keyCloakRepository;

    public UserManagement(UserRepository userRepository, KeyCloakRepository keyCloakRepository) {
        this.userRepository = userRepository;
        this.keyCloakRepository = keyCloakRepository;
    }

    public User createUser(User user) {
        String keyCloakId = keyCloakRepository.createKeycloakUser(
                user.getFirstname(),
                user.getLastname(),
                user.getPhone());

        user.setKeycloakId(keyCloakId);

        return userRepository.insert(user);
    }

    public User updateUser(User user) {
        boolean firstUpdate = keyCloakRepository.updateKeycloakUser(
                user.getKeycloakId(),
                user.getFirstname(),
                user.getLastname(),
                user.getPhone(),
                user.getEmail());

        if(firstUpdate){
            return userRepository.update(user);
        }else {
            throw new RuntimeException("Failed to update user in Keycloak");
        }

    }
}
