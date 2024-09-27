package com.vincentrungogh.global.auth.service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SignupRequest {

    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 1, max = 20, message = "패스워드는 1글자 이상 20글자 이하로 입력해주세요.")
    private String password;
    @NotBlank
    @Size(min = 1, max = 10, message = "닉네임은 1글자 이상 10글자 이하로 입력해주세요.")
    private String nickname;
    @NotNull
    private int gender;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birth;
    @NotNull
    private double height;
    @NotNull
    private double weight;
}
