package com.vincentrungogh.domain.drawing.repository;

import com.vincentrungogh.domain.drawing.entity.Drawing;

import java.util.List;

public interface DrawingDetailRepositoryCustom {
    double findByDrawingAverageSpeed(Drawing drawing);

    int findByDrawingSumDistance(Drawing drawing);

    List<String> findAllIdsByDrawing(Drawing drawing);

}
