package com.vincentrungogh.domain.myhealth.service;

import com.vincentrungogh.domain.drawing.repository.DrawingDetailRepository;
import com.vincentrungogh.domain.myhealth.entity.MyHealth;
import com.vincentrungogh.domain.myhealth.repository.MyHealthRepository;
import com.vincentrungogh.domain.myhealth.service.dto.response.EachMonthMyhealthResponse;
import com.vincentrungogh.domain.user.entity.User;
import com.vincentrungogh.domain.user.repository.UserRepository;
import com.vincentrungogh.global.exception.CustomException;
import com.vincentrungogh.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyHealthService {

    // 필요한 의존성 주입
    private final MyHealthRepository myHealthRepository;
    private final UserRepository userRepository;
    private final DrawingDetailRepository drawingDetailRepository;

    // 유저 생성 시 마이헬스 조회
    public MyHealth getMyHealth(User user) {
        return myHealthRepository.findByUser(user).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
    }

    // 드로잉 활동 정보 조회
    @Transactional
    public EachMonthMyhealthResponse getDrawings(int userId, int year) {

        // 0. EachMonthMyhealthResponse 객체 생성을 위한 field 초기값 세팅
        int[] walkList = new int[12];
        double[] distanceList = new double[12];
        int[] timeList = new int[12];
        int[] completedRouteDrawingList = new int[12];
        int[] completedFreeDrawingList = new int[12];
        int totalWalk;
        double totalDistance;
        int totalTime;
        int totalCompletedDrawing;

        // 1. 유저 아이디로 유저 엔티티 특정하기
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        // 2-1. 할당된 유저 엔티티, 받은 연도, 모든 달을 기준으로 QueryDSL
        List<EachMonthWalkDistanceTime> eachMonthWalkDistanceTime = drawingDetailRepository.findWalkDistanceTimeByYearEachMonth(user, year);
        /** EachMonthWalkDistanceTime은 field로 달, 총걸음수, 총거리, 총시간 */






        return null;
    }
}
