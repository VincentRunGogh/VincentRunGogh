package com.vincentrungogh.domain.drawing.service.dto.response;

import com.vincentrungogh.domain.drawing.entity.Drawing;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DrawingResponseDto {
    private String title;
    private String drawingImage;
    private Double avgSpeed;
    private int distance;

    @Builder
    private DrawingResponseDto(String title, String drawingImage, Double avgSpeed, int distance) {
        this.title = title;
        this.drawingImage = drawingImage;
        this.avgSpeed = avgSpeed;
        this.distance = distance;
    }

    public static DrawingResponseDto createDrawingResponseDto(Drawing drawing, Double avgSpeed, int distance) {
        return DrawingResponseDto.builder()
                .title(drawing.getTitle())
                .drawingImage(drawing.getAccumulatedDrawingImage())
                .avgSpeed(Math.round(avgSpeed*(3.6) * 100.0) / 100.0)
                .distance(distance)
                .build();
    }
}
