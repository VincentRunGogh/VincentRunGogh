package com.vincentrungogh.domain.calendar.controller;

import com.vincentrungogh.domain.calendar.service.CalendarService;
import com.vincentrungogh.domain.calendar.service.dto.response.MonthCalendarResponse;
import com.vincentrungogh.global.auth.service.dto.response.UserPrincipal;
import com.vincentrungogh.global.util.CommonSwaggerResponse;
import com.vincentrungogh.global.util.ResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
@Slf4j
public class CalendarController {

    private final CalendarService calendarService;

    // 월별 드로잉 데이터 조회
    @Operation(summary = "월별 드로잉 데이터 조회", description = "월별 드로잉 데이터 조회하기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "월별 드로잉 데이터 조회 요청이 성공했습니다.",
                    content = @Content(schema = @Schema(implementation = MonthCalendarResponse.class))), // 전체 응답 프레임 중 실제 데이터 프레임
            @ApiResponse(responseCode = "500", description = "월별 드로잉 데이터 조회 요청이 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ResultDto.class))) // 전체 응답 프레임
    })
    @CommonSwaggerResponse.CommonResponses
    @GetMapping("")
    public ResponseEntity<?> getCalendar(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam Integer year, @RequestParam Integer month) {
        log.info("Calendar Controller 중 월별 드로잉 데이터 조회 메서드 실행");
        MonthCalendarResponse response = calendarService.getCalendar(userPrincipal.getId(), year, month);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResultDto.res(HttpStatus.OK.value(),
                        "월별 드로잉 데이터 조회 요청이 성공하였습니다.", response));
    }

}