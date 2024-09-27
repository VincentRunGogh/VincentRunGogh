package com.vincentrungogh.domain.running.service.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RunningRequest {
    private double lat;
    private double lng;
    private double speed;
    private double time;
}
