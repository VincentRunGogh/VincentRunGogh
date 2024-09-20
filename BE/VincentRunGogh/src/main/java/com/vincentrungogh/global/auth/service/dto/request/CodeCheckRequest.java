package com.vincentrungogh.global.auth.service.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeCheckRequest {
    @NotBlank
    private String code;
    @NotBlank
    private String email;
}
