package com.vincentrungogh.domain.drawing.service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DrawingRealTimeRequest {
    @NotNull
    private Double lat;
    @NotNull
    private Double lng;
    @NotNull
    private Double speed;

    @Builder
    private DrawingRealTimeRequest(Double lat, Double lng, Double speed) {
        this.lat = lat;
        this.lng = lng;
        this.speed = speed;
    }

    public static DrawingRealTimeRequest createDrawingRealTimeRequest(Double lat, Double lng, Double speed) {
        return DrawingRealTimeRequest.builder()
                .lat(lat)
                .lng(lng)
                .speed(speed)
                .build();
    }
}
