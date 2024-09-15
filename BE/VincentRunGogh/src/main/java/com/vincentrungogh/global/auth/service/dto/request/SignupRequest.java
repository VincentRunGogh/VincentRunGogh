package com.vincentrungogh.global.auth.service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.util.Date;

@Getter
public class SignupRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;
    @NotNull
    private int gender;
    @NotNull
    private Date birth;
    @NotNull
    private double height;
    @NotNull
    private double weight;
}
