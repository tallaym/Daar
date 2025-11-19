package com.daar.adapter.in.rest.controller.handler;

import com.daar.adapter.in.rest.mapper.AuthMapper;
import com.daar.adapter.in.rest.request.auth.LoginRequest;
import com.daar.core.port.in.dto.auth.login.AuthDTO;
import com.daar.core.port.in.dto.auth.login.query.KeycloakData;
import com.daar.core.port.in.dto.auth.login.query.LoginQuery;
import com.daar.core.port.in.usecase.auth.AuthUseCase;

public class AuthHandler {

    private final AuthUseCase useCase;

    public AuthHandler(AuthUseCase useCase) {
        this.useCase = useCase;
    }

    public AuthDTO<KeycloakData> login(LoginRequest request){
        LoginQuery query = AuthMapper.toQuery(request);
        return useCase.login(query);
    }


}
