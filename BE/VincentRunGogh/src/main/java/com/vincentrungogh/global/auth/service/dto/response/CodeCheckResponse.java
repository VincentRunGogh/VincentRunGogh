package com.vincentrungogh.global.auth.service.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeCheckResponse {
    private Boolean isAvailable;

    @Builder
    private CodeCheckResponse(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public static CodeCheckResponse createCodeCheckResponse(Boolean isAvailable) {
        return CodeCheckResponse.builder()
                .isAvailable(isAvailable)
                .build();
    }
}
