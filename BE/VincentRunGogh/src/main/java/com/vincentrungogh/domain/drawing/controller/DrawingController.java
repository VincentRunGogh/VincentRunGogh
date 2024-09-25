package com.vincentrungogh.domain.drawing.controller;

import com.vincentrungogh.domain.drawing.service.DrawingService;
import com.vincentrungogh.domain.drawing.service.dto.request.DrawingRealTimeRequest;
import com.vincentrungogh.domain.route.service.dto.response.FindRouteResponseDto;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drawings")
@RequiredArgsConstructor
@Slf4j
public class DrawingController {

    private final DrawingService drawingService;

    @Operation(summary = "실시간 드로잉 저장", description = "사용자의 드로잉 GPS 저장")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "실시간 드로잉 저장에 성공했습니다.",
                    content = @Content(schema = @Schema(implementation = ResultDto.class))),
            @ApiResponse(responseCode = "500", description = "실시간 드로잉 저장에 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ResultDto.class)))
    })
    @CommonSwaggerResponse.CommonResponses
    @PostMapping("/real-time")
    public ResponseEntity<?> saveDrawingRealTime(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody DrawingRealTimeRequest request){

        drawingService.saveDrawingRealTime(userPrincipal.getId(), request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResultDto.res(HttpStatus.OK.value(), "실시간 드로잉 저장에 성공했습니다."));
    }
}
