package com.vincentrungogh.global.auth.service;

import com.vincentrungogh.global.auth.service.dto.request.LoginRequest;
import com.vincentrungogh.global.auth.service.dto.response.LoginResponse;
import com.vincentrungogh.global.auth.service.dto.response.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authorizationManager;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest loginRequest) {
        log.info("AuthService : 로그인 시작");
        Authentication authentication = authorizationManager.authenticate(
                // 아이디, 패스워드 입력
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String accessToken = jwtService.buildAccessToken(userPrincipal.getId());
        String refreshToken = jwtService.buildRefreshToken(userPrincipal.getId());

        return LoginResponse.createLoginResponse(
                accessToken,refreshToken
        );
    }
}
