package com.daar.core.domain.service;

import com.daar.core.domain.model.auth.User;
import com.daar.core.domain.repository.auth.KeyCloakRepository;
import com.daar.core.domain.repository.auth.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserManagementTest {

    private UserRepository userRepository;
    private KeyCloakRepository keyCloakRepository;
    private UserManagement userManagement;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        keyCloakRepository = mock(KeyCloakRepository.class);
        userManagement = new UserManagement(userRepository, keyCloakRepository);
    }

    @Test
    void createUser_shouldCreateInKeycloakAndInsertInRepository() {
        User user = User.builder()
                .firstname("John")
                .lastname("Doe")
                .phone("778889900")
                .build();

        when(keyCloakRepository.createKeycloakUser("John", "Doe", "778889900"))
                .thenReturn("kc123");

        User savedUser = User.builder()
                .keycloakId("kc123")
                .firstname("John")
                .lastname("Doe")
                .phone("778889900")
                .build();

        when(userRepository.insert(any(User.class)))
                .thenReturn(savedUser);

        User result = userManagement.createUser(user);

        // Vérification que Keycloak a été appelé
        verify(keyCloakRepository).createKeycloakUser("John", "Doe", "778889900");

        // Vérification que le user envoyé à insert contient le bon keycloakId
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).insert(captor.capture());
        assertEquals("kc123", captor.getValue().getKeycloakId());

        // Vérification du résultat
        assertEquals("kc123", result.getKeycloakId());
    }

    @Test
    void updateUser_shouldUpdateWhenKeycloakReturnsTrue() {
        User user = User.builder()
                .keycloakId("kc123")
                .firstname("New")
                .lastname("Name")
                .phone("770000000")
                .email("test@mail.com")
                .build();

        when(keyCloakRepository.updateKeycloakUser(
                "kc123", "New", "Name", "770000000", "test@mail.com"
        )).thenReturn(true);

        when(userRepository.update(user))
                .thenReturn(user);

        User result = userManagement.updateUser(user);

        verify(keyCloakRepository).updateKeycloakUser(
                "kc123", "New", "Name", "770000000", "test@mail.com");

        verify(userRepository).update(user);
        assertEquals(user, result);
    }

    @Test
    void updateUser_shouldThrowExceptionWhenKeycloakFails() {
        User user = User.builder()
                .keycloakId("kc123")
                .firstname("New")
                .lastname("Name")
                .phone("770000000")
                .email("test@mail.com")
                .build();

        when(keyCloakRepository.updateKeycloakUser(
                "kc123", "New", "Name", "770000000", "test@mail.com"
        )).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            userManagement.updateUser(user);
        });

        verify(userRepository, never()).update(any());
    }
}