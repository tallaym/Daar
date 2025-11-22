package com.daar.adapter.in.rest.controller;

import com.daar.adapter.in.rest.auth.controller.UserController;
import com.daar.core.usecase.auth.usecase.AuthUseCase;
import com.daar.core.usecase.auth.usecase.UserUseCase;
import io.javalin.http.Context;
import org.junit.jupiter.api.BeforeEach;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class UserControllerTest {

    private UserController userController;
    private UserUseCase userUseCase;
    private AuthUseCase authUseCase;
    private Context ctx;

    @BeforeEach
    void setUp(){
        userUseCase = mock(UserUseCase.class);
        authUseCase = mock(AuthUseCase.class);
        userController = new UserController(userUseCase);
        ctx = mock(Context.class);
    }

    /*
    @Test
    void testInsertHandler(){

        CreateUserRequest request = new CreateUserRequest(
                "Amina",
                "Fofana",
                "770661163",
                null
        );
        when(ctx.bodyAsClass(CreateUserRequest.class)).thenReturn(request);

        AuthDTO<String> authDTO = new AuthDTO<>(true, "Registration successful. Keycloak ID: some-id");
        when(authUseCase.register(any(CreateKeycloakUserCommand.class))).thenReturn(authDTO);

        UserDTO dto = new UserDTO(UUID.randomUUID(), "some-id", "Amina", "Fofana", "770661163", UUID.randomUUID());
        when(userUseCase.create(any(CreateUserCommand.class))).thenReturn(dto);

        userController.registerRoutes(mock(Javalin.class));

        try{
            var method = UserController.class.getDeclaredMethod("insertHandler", Context.class);
            method.setAccessible(true);
            method.invoke(userController, ctx);
        } catch (Exception e){
            fail(e);
        }

        ArgumentCaptor<CreateUserCommand> commandCaptor = ArgumentCaptor.forClass(CreateUserCommand.class);
        verify(userUseCase).create(commandCaptor.capture());
        assertEquals("Amina", commandCaptor.getValue().getFirstname());
        assertEquals("Fofana", commandCaptor.getValue().getLastname());
        assertEquals("770661163", commandCaptor.getValue().getPhone());

        verify(ctx).json(dto);
        verify(ctx).status(201);
    }
    */

}
