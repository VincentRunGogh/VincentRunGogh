package com.vincentrungogh.domain.drawing.service.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DataSaveDrawingDetailResponse {

    private String drawingDetailId;
    private int distance;
    private int time;
    private int speed;
}
