package com.vincentrungogh.domain.drawing.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveDrawingRequest {

    @NotBlank
    private String drawingImage;
    @NotBlank
    private String drawingDetailImage;
    @NotNull
    private Integer step;
}
