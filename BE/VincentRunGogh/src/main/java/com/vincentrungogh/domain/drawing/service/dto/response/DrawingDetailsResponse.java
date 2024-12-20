package com.vincentrungogh.domain.drawing.service.dto.response;

import com.vincentrungogh.domain.drawing.entity.Drawing;
import com.vincentrungogh.domain.drawing.entity.DrawingDetail;
import com.vincentrungogh.domain.drawing.entity.DrawingDetailsSummary;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DrawingDetailsResponse {

    private int totalTime;
    private double totalDistance;
    private int totalStep;
    private double avgPace;
    @Setter
    private String routeImage;
    private List<DrawingDetailResponseDto> drawingDetails;

    @Builder
    private DrawingDetailsResponse(List<DrawingDetailResponseDto> drawingDetails, int totalTime, double totalDistance, int totalStep,
                                   String routeImage, double avgPace) {
        this.drawingDetails = drawingDetails;
        this.totalTime = totalTime;
        this.totalDistance = totalDistance;
        this.totalStep = totalStep;
        this.routeImage = routeImage;
        this.avgPace = avgPace;
    }

    public static DrawingDetailsResponse createDrawingDetailsResponse(List<DrawingDetail> drawingDetails,
                                                                      DrawingDetailsSummary summary) {

        List<DrawingDetailResponseDto> list = new ArrayList<>();
        for(DrawingDetail detail : drawingDetails) {
            list.add(DrawingDetailResponseDto.createDrawingDetailResponseDto(detail));
        }

        double avgSpeed = Math.round(summary.getAvgSpeed() * 100) / 100.0;
        double avgPace = 0;
        if(avgSpeed > 0.0) {
            avgPace = (int) Math.round(3600 / avgSpeed);
        }

        double distance = Math.round((summary.getTotalDistance() / 1000.0)* 100) / 100.0;

        return DrawingDetailsResponse
                .builder()
                .totalDistance(distance)
                .totalTime(summary.getTotalTime())
                .avgPace(avgPace)
                .totalStep(summary.getTotalStep())
                .drawingDetails(list)
                .routeImage(null)
                .build();
    }

}
