package com.vincentrungogh.global.auth.controller;


import com.vincentrungogh.global.auth.service.AuthService;
import com.vincentrungogh.global.auth.service.dto.request.LoginRequest;
import com.vincentrungogh.global.auth.service.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){

        log.info("AuthController : 로그인 시작");
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
