package com.vincentrungogh.domain.drawing.service.dto.request;

import com.vincentrungogh.domain.running.service.dto.request.RunningRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DataSaveDrawingDetailRequset {

    private List<RunningRequest> positionTimeList;

    @Builder
    private DataSaveDrawingDetailRequset(List<RunningRequest> positionTimeList) {
        this.positionTimeList = positionTimeList;
    }

    public static DataSaveDrawingDetailRequset createDataSaveDrawingDetailRequset(List<RunningRequest> positionTimeList) {
        return DataSaveDrawingDetailRequset.builder()
                .positionTimeList(positionTimeList)
                .build();
    }
}
