package com.vincentrungogh.global.util;

public class DistanceCalculator {

    private static final int EARTH_RADIUS = 6371000;

    public static int calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        double latDistance = Math.toRadians(lat2 - lat1);
        double lngDistance = Math.toRadians(lng2 - lng1);

        // harversine 공식
        // 라디안으로 변환 후 거리 계산
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) // 수직방향 곡률 계산
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) // 위도의 기울기 각각 곱
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2); // 수평방향 곡률 계산

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)); // 중심각 구하기
        double distance = EARTH_RADIUS * c;
        return (int) Math.round(distance);
    }
}