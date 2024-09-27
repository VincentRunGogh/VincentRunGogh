package com.vincentrungogh.domain.drawing.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vincentrungogh.domain.drawing.entity.Drawing;
import com.vincentrungogh.domain.drawing.entity.QDrawingDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DrawingDetailRepositoryImpl implements DrawingDetailRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final QDrawingDetail drawingDetail = QDrawingDetail.drawingDetail;

    @Override
    public double findByDrawingAverageSpeed(Drawing drawing) {
        Double avgSpeed = queryFactory.select(drawingDetail.speed.avg())
                .from(drawingDetail)
                .where(drawingDetail.drawing.eq(drawing))
                .fetchOne();

        return Optional.ofNullable(avgSpeed).orElse(0.0);
    }

    @Override
    public int findByDrawingSumDistance(Drawing drawing) {
        Integer distance = queryFactory.select(drawingDetail.distance.sum())
                .from(drawingDetail)
                .where(drawingDetail.drawing.eq(drawing))
                .fetchOne();

        return Optional.ofNullable(distance).orElse(0);
    }

    @Override
    public List<String> findAllIdsByDrawing(Drawing drawing) {
        return queryFactory
                .select(drawingDetail.id)
                .from(drawingDetail)
                .where(drawingDetail.drawing.eq(drawing))
                .fetch();
    }
}
