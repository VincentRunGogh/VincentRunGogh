package com.vincentrungogh.domain.drawing.service.strategy;

import com.vincentrungogh.domain.drawing.entity.Drawing;
import com.vincentrungogh.domain.drawing.repository.DrawingRepository;
import com.vincentrungogh.domain.drawing.service.dto.common.FindDrawing;
import com.vincentrungogh.domain.drawing.service.dto.response.DrawingListResponseDto;
import com.vincentrungogh.domain.route.entity.Route;
import com.vincentrungogh.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommunityDrawingStrategy implements DrawingStrategy {

    private final DrawingRepository drawingRepository;

    @Override
    public DrawingListResponseDto findDrawingList(User user) {
        //완료되었지만 커뮤니티에 올리지 않은 드로잉 조회
        List<Drawing> drawingList = drawingRepository.findAllByUserAndIsCreatedBoard(user, false);

        List<FindDrawing> findDrawingList = drawingList.stream()
                .map(drawing -> {
                    //루트가 존재할 경우 루트의 아트 이미지 가져오기
                    Optional<Route> route = Optional.ofNullable(drawing.getRoute());

                    return FindDrawing.createFindDrawing(drawing, route);
                })
                .toList();

        return DrawingListResponseDto.createDrawingList(findDrawingList);
    }
}
