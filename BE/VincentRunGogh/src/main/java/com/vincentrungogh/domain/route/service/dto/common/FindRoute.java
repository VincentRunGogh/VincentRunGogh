package com.vincentrungogh.domain.route.service.dto.common;

import com.vincentrungogh.domain.route.entity.Route;
import com.vincentrungogh.global.exception.CustomException;
import com.vincentrungogh.global.exception.ErrorCode;
import com.vincentrungogh.global.util.DistanceCalculator;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindRoute {
    private String routeId;
    private String title;
    private String artImage;
    private int distance;
    private int predictTime;
    private int distanceFromUser;


    @Builder
    private FindRoute(String routeId, String title, String artImage, int distance, int predictTime, int distanceFromUser) {
        this.routeId = routeId;
        this.title = title;
        this.artImage = artImage;
        this.distance = distance;
        this.predictTime = predictTime;
        this.distanceFromUser = distanceFromUser;
    }

    public static FindRoute createFindRoute(Route route, Double lat, Double lng, Double averageSpeed) {

        //사용자로부터 떨어진 거리
        int distanceUser = DistanceCalculator.calculateDistance(route.getCenterLat(), route.getCenterLng(), lat, lng);

        if (averageSpeed <= 0) {
            throw new CustomException(ErrorCode.SPEED_DIVIDE_BY_ZERO);
        }

        int predictTime = (int) (route.getDistance() / averageSpeed);

        return FindRoute.builder()
                .routeId(route.getId())
                .title(route.getTitle())
                .artImage(route.getArtImage())
                .distance(route.getDistance())
                .predictTime(predictTime)
                .distanceFromUser(distanceUser)
                .build();
    }
}
