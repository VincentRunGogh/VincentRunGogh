package com.vincentrungogh.domain.running.service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RunningRequest {
    @NotNull
    private Double lat;
    @NotNull
    private Double lng;
    @NotNull
    private String time;
}
