package com.vincentrungogh.domain.user.service.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeekExerciseResponse {

    private List<Integer> week;

    @Builder
    private WeekExerciseResponse(List<Integer> week) {
        this.week = week;
    }

    public static WeekExerciseResponse createWeekExerciseResponse(List<Integer> week) {
        return WeekExerciseResponse.builder()
                .week(week)
                .build();
    }
}
