package com.vincentrungogh.domain.board.service.strategy;

import com.vincentrungogh.domain.board.entity.Board;
import com.vincentrungogh.domain.board.repository.BoardRepository;
import com.vincentrungogh.domain.board.repository.UserLikeRepository;
import com.vincentrungogh.domain.board.service.dto.common.FindBoard;
import com.vincentrungogh.domain.board.service.dto.response.FindBoardResponseDto;
import com.vincentrungogh.domain.myhealth.entity.MyHealth;
import com.vincentrungogh.domain.myhealth.repository.MyHealthRepository;
import com.vincentrungogh.domain.user.entity.User;
import com.vincentrungogh.global.exception.CustomException;
import com.vincentrungogh.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service("FindAllBoard")
@RequiredArgsConstructor
public class FindAllBoard implements BoardStrategy {

    private final BoardRepository boardRepository;
    private final UserLikeRepository userLikeRepository;
    private final MyHealthRepository myHealthRepository;

    @Override
    public FindBoardResponseDto findBoard(User user, double lat, double lng) {

        MyHealth myHealth = myHealthRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(ErrorCode.MYHEALTH_NOT_FOUND));

        double averageSpeed = myHealth.getAverageSpeed();
        log.info("내 속도 : " + averageSpeed);
        boolean averageSpeedUnderZero = averageSpeed <= 0;

        // 모든 게시글 sql에서 조회하기
        List<Board> boardList = boardRepository.findAllByOrderByCreatedDesc();

        log.info("boardList "+ boardList);

        List<FindBoard> findBoardList = boardList.stream()
                .filter(board -> !board.getIsDelete())
                .map(board -> {
                    boolean isLiked = userLikeRepository.findByUserAndBoard(user, board).isPresent();
                    int predictedTime = averageSpeedUnderZero ? 0: (int) ((board.getRoute().getDistance() / 1000.0) / averageSpeed * 3600) ;
                    return FindBoard.createFindBoard(board, lat, lng, isLiked, predictedTime);
                })
                .filter(Objects::nonNull) // null 값 제거
                .toList();

        log.info("findALLBoardList "+ findBoardList);

        return FindBoardResponseDto.createFindBoardResponseDto(findBoardList);

    }


}
