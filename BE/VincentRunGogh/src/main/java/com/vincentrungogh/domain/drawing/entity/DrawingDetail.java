package com.vincentrungogh.domain.drawing.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "drawing_detail")
public class DrawingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted;

    @Column(name = "time")
    private int time;

    @Column(name = "distance")
    private int distance;

    @Column(name = "speed")
    private double speed;

    @Column(name = "step")
    private int step;

    @Column(name = "current_drawing_image")
    private String currentDrawingImage;

    @Column(name = "created", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drawing_id")
    private Drawing drawing;

    @Builder
    private DrawingDetail(Boolean isCompleted, int time, int distance, double speed, int step, String currentDrawingImage, LocalDateTime created, Drawing drawing) {
        this.isCompleted = isCompleted;
        this.time = time;
        this.distance = distance;
        this.speed = speed;
        this.step = step;
        this.currentDrawingImage = currentDrawingImage;
        this.created = created;
    }

    public static DrawingDetail createDrawingDetail(Drawing drawing){
        return DrawingDetail.builder()
                .isCompleted(false)
                .time(0)
                .distance(0)
                .speed(0)
                .step(0)
                .currentDrawingImage(null)
                .created(LocalDateTime.now())
                .drawing(drawing)
                .build();
    }

    // 드로잉이 일시 정지 되었을 때
    public void stopDrawing(int time, int distance, int speed, int step, String currentDrawingImage){
        this.time = time;
        this.distance = distance;
        this.speed = speed;
        this.step = step;
        this.currentDrawingImage = currentDrawingImage;
    }

    // 드로잉이 완료되었을 때
    public void completeDrawing(int time, int distance, int speed, int step, String currentDrawingImage){
        this.time = time;
        this.distance = distance;
        this.speed = speed;
        this.step = step;
        this.currentDrawingImage = currentDrawingImage;
        this.isCompleted = true;
    }
}
