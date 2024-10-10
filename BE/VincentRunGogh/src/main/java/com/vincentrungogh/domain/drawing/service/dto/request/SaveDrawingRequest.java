package com.vincentrungogh.domain.drawing.service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vincentrungogh.domain.route.service.dto.common.Position;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveDrawingRequest {

    @NotBlank
    private String drawingImage;
    @NotBlank
    private String drawingDetailImage;
    @NotNull
    private Integer step;
    @NotNull
    private Double lat;
    @NotNull
    private Double lng;
    @NotBlank
    private String time;
}