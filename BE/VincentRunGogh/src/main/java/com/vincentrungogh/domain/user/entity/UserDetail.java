package com.vincentrungogh.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "user_detail")
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "gener", nullable = false)
    private int gender;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "average_speed")
    private double average_speed;

    @Column(name = "average_day_distance")
    private double average_day_distance;

    @Column(name = "average_day_runtime")
    private double average_day_runtime;

    @Builder
    private UserDetail(int gender, int age, int average_speed, double average_day_distance, double average_day_runtime) {
        this.gender = gender;
        this.age = age;
        this.average_speed = average_speed;
        this.average_day_distance = average_day_distance;
        this.average_day_runtime = average_day_runtime;
    }

    public static UserDetail createUserDetail(int gender, int age, int average_speed, double average_day_distance, double average_day_runtime) {
        return UserDetail.builder()
                .gender(gender)
                .age(age)
                .average_speed(average_speed)
                .average_day_distance(average_day_distance)
                .average_day_runtime(average_day_runtime)
                .build();
    }

    public void updateUserDetail(int average_speed, double average_day_distance, double average_day_runtime) {
        this.average_speed = average_speed;
        this.average_day_distance = average_day_distance;
        this.average_day_runtime = average_day_runtime;
    }
}
