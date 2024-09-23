package com.vincentrungogh.domain.route.service.dto.response;

import com.vincentrungogh.domain.route.entity.Route;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveRouteResponseDto {
    private String routeId;
    private String artImage;
    private int distance;
    private int predictTime;

    @Builder
    private SaveRouteResponseDto(String routeId, String artImage, int distance, int predictTime) {
        this.routeId = routeId;
        this.artImage = artImage;
        this.distance = distance;
        this.predictTime = predictTime;
    }

    public static SaveRouteResponseDto createSaveRouteResponseDto(Route route, double averageSpeed) {
        return SaveRouteResponseDto.builder()
                .routeId(route.getId())
                .artImage(route.getArtImage())
                .distance(route.getDistance())
                .predictTime((int) (route.getDistance() / averageSpeed))
                .build();
    }
}
