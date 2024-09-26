package com.vincentrungogh.domain.drawing.repository;

import com.vincentrungogh.domain.drawing.entity.Drawing;

public interface DrawingDetailRepositoryCustom {
    double findByDrawingAverageSpeed(Drawing drawing);

    int findByDrawingSumDistance(Drawing drawing);
}
