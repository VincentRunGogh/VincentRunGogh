package com.vincentrungogh.domain.drawing.repository;

import com.vincentrungogh.domain.drawing.entity.Drawing;
import com.vincentrungogh.domain.drawing.entity.DrawingDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DrawingDetailRepository extends JpaRepository<DrawingDetail, Integer>, DrawingDetailRepositoryCustom {
    List<DrawingDetail> findAllByDrawingAndCreatedBetween(Drawing drawing, LocalDateTime start, LocalDateTime end);

    @Override
    double findByDrawingAverageSpeed(Drawing drawing);

    @Override
    int findByDrawingSumDistance(Drawing drawing);
}
