package com.vincentrungogh.domain.drawing.service;

import com.vincentrungogh.domain.drawing.repository.DrawingRepository;
import com.vincentrungogh.domain.drawing.service.dto.request.DrawingRealTimeRequest;
import com.vincentrungogh.global.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class DrawingService {

    private final RedisService redisService;
    private final DrawingRepository drawingRepository;

    public void saveDrawingRealTime(int userId, DrawingRealTimeRequest request){
        redisService.saveDrawingRealTime(userId, request);
    }
}
