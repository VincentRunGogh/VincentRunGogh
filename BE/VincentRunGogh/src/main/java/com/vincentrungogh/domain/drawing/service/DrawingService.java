package com.vincentrungogh.domain.drawing.service;

import com.vincentrungogh.domain.drawing.entity.Drawing;
import com.vincentrungogh.domain.drawing.repository.DrawingDetailRepository;
import com.vincentrungogh.domain.drawing.repository.DrawingRepository;
import com.vincentrungogh.domain.drawing.service.dto.response.DrawingResponseDto;
import com.vincentrungogh.domain.user.entity.User;
import com.vincentrungogh.domain.user.repository.UserRepository;
import com.vincentrungogh.global.exception.CustomException;
import com.vincentrungogh.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DrawingService {

    private final DrawingDetailRepository drawingDetailRepository;
    private final DrawingRepository drawingRepository;
    private final UserRepository userRepository;

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
}
