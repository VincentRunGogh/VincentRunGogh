package com.vincentrungogh.global.auth.service;

import com.vincentrungogh.global.auth.service.dto.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    public void login(LoginRequest loginRequest) {
    }
}
