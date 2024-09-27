package com.vincentrungogh.domain.drawing.service;

import com.vincentrungogh.domain.drawing.entity.DrawingDetail;
import com.vincentrungogh.domain.drawing.entity.MongoDrawingDetail;
import com.vincentrungogh.domain.drawing.repository.MongoDrawingRepository;
import com.vincentrungogh.domain.drawing.service.dto.response.RestartDrawingResponse;
import com.vincentrungogh.domain.drawing.service.dto.response.StartDrawingResponse;
import com.vincentrungogh.domain.route.entity.MongoRoute;
import com.vincentrungogh.domain.route.entity.Route;
import com.vincentrungogh.domain.route.repository.MongoRouteRepository;
import com.vincentrungogh.domain.route.repository.RouteRepository;
import com.vincentrungogh.domain.route.service.dto.common.Position;
import com.vincentrungogh.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.vincentrungogh.domain.drawing.entity.Drawing;
import com.vincentrungogh.domain.drawing.repository.DrawingDetailRepository;
import com.vincentrungogh.domain.drawing.repository.DrawingRepository;
import com.vincentrungogh.domain.drawing.service.dto.response.DrawingResponseDto;
import com.vincentrungogh.domain.user.entity.User;
import com.vincentrungogh.domain.user.repository.UserRepository;
import com.vincentrungogh.global.exception.CustomException;
import com.vincentrungogh.global.exception.ErrorCode;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class DrawingService {

    private final DrawingDetailRepository drawingDetailRepository;
    private final DrawingRepository drawingRepository;
    private final UserRepository userRepository;
    private final RouteRepository routeRepository;
    private final MongoDrawingRepository mongoDrawingRepository;
    private final MongoRouteRepository mongoRouteRepository;
    private final UserService userService;

    @Transactional
    public DrawingResponseDto getDrawing(int userId, int drawingId) {
        //유저 존재하지 않으면 에러
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        //드로잉 존재하지 않으면 에러
        Drawing drawing = drawingRepository.findById(drawingId).orElseThrow(
                () -> new CustomException(ErrorCode.DRAWING_NOT_FOUND)
        );

        //드로잉 디테일에서 평균 속력이랑 총 거리 구하기
        double avgSpeed = drawingDetailRepository.findByDrawingAverageSpeed(drawing);
        int distance = drawingDetailRepository.findByDrawingSumDistance(drawing);

        return DrawingResponseDto.createDrawingResponseDto(drawing,  avgSpeed, distance);
    }

    @Transactional
    public StartDrawingResponse startDrawing(int userId, String routeId) {

        // 1. 유저 여부
        User user = userService.getUserById(userId);

        // 2. route 여부
        if(routeId == null) {
            // 자유 달리기
            return freeRunning(user);
        }
        return drawingRunning(routeId, user);
    }

    private StartDrawingResponse freeRunning(User user) {
        // 1. 드로잉 생성
        Drawing drawing = Drawing
                .createFreeRunning(user);

        drawing = drawingRepository.save(drawing);
        return StartDrawingResponse
                .createStartFreeRunningResponse(drawing.getId());
    }


    private StartDrawingResponse drawingRunning(String routeId, User user) {
        // 1. 루트 찾기
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new CustomException(ErrorCode.ROUTE_NOT_FOUND));

        // 2. 드로잉 생성
        Drawing drawing = Drawing
                .createDrawing(route.getTitle(),
                        user,
                        route);
        drawing = drawingRepository.save(drawing);

        // 3. 루트 정보 가져오기
        MongoRoute mongoRoute = mongoRouteRepository.findById(routeId)
                .orElseThrow(() -> new CustomException(ErrorCode.ROUTE_NOT_FOUND));

        return StartDrawingResponse
                .createStartDrawingResponse(drawing.getTitle(),
                        drawing.getId(), mongoRoute.getPositionList());

    }

    public RestartDrawingResponse restartDrawing(int drawingId){
        // 0. 드로잉 찾기
        Drawing drawing = drawingRepository.findById(drawingId)
                .orElseThrow(() -> new CustomException(ErrorCode.DRAWING_NOT_FOUND));

        // 1. 드로잉디테일 찾기
        List<String> drawingDetailIds = drawingDetailRepository.findAllIdsByDrawing(drawing);

        // 2. 드로잉 디테일 정보 찾기
        List<MongoDrawingDetail> mongoDrawingPositionList = mongoDrawingRepository.findAllByIdIn(drawingDetailIds);

        // 3.
        List<Position> drawingPositionList = mongoDrawingPositionList.stream()
                .flatMap(mongoDrawing -> mongoDrawing.getPositionList().stream())
                .toList();

        // 4. 루트 정보
        List<Position> routePositionList = mongoRouteRepository.findById(drawing.getRoute().getId())
                .orElseThrow(() -> new CustomException(ErrorCode.ROUTE_NOT_FOUND)).getPositionList();

        return RestartDrawingResponse.createRestartDrawingResponse(
                drawing.getTitle(),
                drawingPositionList,
                routePositionList
        );
    }
}
