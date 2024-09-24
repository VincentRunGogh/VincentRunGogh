package com.vincentrungogh.domain.user.service.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeekExerciseResponse {

    private int[] week = new int[7];

    @Builder
    private WeekExerciseResponse(int[] week) {
        this.week = week;
    }

    public static WeekExerciseResponse createWeekExerciseResponse(int[] week) {
        return WeekExerciseResponse.builder()
                .week(week)
                .build();
    }
}
