package com.vincentrungogh.domain.running.service;

import com.vincentrungogh.domain.drawing.entity.Drawing;
import com.vincentrungogh.domain.drawing.repository.DrawingRepository;
import com.vincentrungogh.domain.route.entity.MongoRoute;
import com.vincentrungogh.domain.route.entity.Route;
import com.vincentrungogh.domain.route.repository.MongoRouteRepository;
import com.vincentrungogh.domain.route.repository.RouteRepository;

import com.vincentrungogh.domain.running.service.dto.request.RunningRequest;
import com.vincentrungogh.domain.user.entity.User;
import com.vincentrungogh.domain.user.service.UserService;
import com.vincentrungogh.global.exception.CustomException;
import com.vincentrungogh.global.exception.ErrorCode;
import com.vincentrungogh.global.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RunningService {

    private final RedisService redisService;


    public void running(RunningRequest request, int userId){

        // 레디스 저장
        redisService.saveRunning(userId, request.getPosition());
    }


}