package com.vincentrungogh.domain.running.service.dto.request;

import com.vincentrungogh.domain.running.service.dto.common.RunningPosition;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RunningRequest {

    private RunningPosition position;
    private int drawingId;
}
