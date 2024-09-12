package com.vincentrungogh.domain.myhealth.entity;

import com.vincentrungogh.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "my_health")
public class MyHealth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "total_time")
    private int totalTime;

    @Column(name = "total_distance")
    private int totalDistance;

    @Column(name = "average_speed")
    private double averageSpeed;

    @Column(name = "total_step")
    private int totalStep;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private MyHealth(int totalTime, int totalDistance, double averageSpeed, int totalStep, User user) {
        this.totalTime = totalTime;
        this.totalDistance = totalDistance;
        this.averageSpeed = averageSpeed;
        this.totalStep = totalStep;
        this.user = user;
    }

    public static MyHealth createMyHealth(int totalTime, int totalDistance, double averageSpeed, int totalStep, User user) {
        return MyHealth.builder()
                .totalTime(totalTime)
                .totalDistance(totalDistance)
                .averageSpeed(averageSpeed)
                .totalStep(totalStep)
                .user(user)
                .build();
    }

    public void updateMyHealth(int totalTime, int totalDistance, double averageSpeed, int totalStep){
        this.totalTime = totalTime;
        this.totalDistance = totalDistance;
        this.averageSpeed = averageSpeed;
        this.totalStep = totalStep;
    }
}
