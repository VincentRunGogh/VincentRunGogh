package com.vincentrungogh.global.auth.service.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponse {

    private String accessToken;
    private Boolean isChange;
    private String nickname;

    @Builder
    private LoginResponse(String accessToken, Boolean isChange, String nickname) {
        this.accessToken = accessToken;
        this.isChange = isChange;
        this.nickname = nickname;
    }

    public static LoginResponse createLoginResponse(String accessToken, Boolean isChange, String nickname) {
        return LoginResponse.builder()
                .accessToken(accessToken)
                .isChange(isChange)
                .nickname(nickname)
                .build();
    }
}
