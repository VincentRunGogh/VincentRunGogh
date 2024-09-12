package com.vincentrungogh.domain.route.service;

import com.vincentrungogh.domain.route.repository.RouteRepository;
import com.vincentrungogh.domain.route.service.dto.request.ArtRouteRequestDto;
import com.vincentrungogh.domain.route.service.dto.response.DataArtRouteResponseDto;
import com.vincentrungogh.domain.route.service.dto.response.ArtRouteResponseDto;
import com.vincentrungogh.global.util.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;
//    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public ArtRouteResponseDto convertArtRoute(/*@AuthenticationPrincipal UserPrincipal principal,*/ArtRouteRequestDto requestDto) {
        //1. 사용자 확인

        //2. request한 것을 토대로 파이썬으로 보내기
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:8000")
                .path("/api/v1/rootings/art")
                .encode()
                .build()
                .toUri();

        //파이썬으로 post 요청 보내기
        ResponseEntity<ResultDto> dataRequest = restTemplate.postForEntity(uri, requestDto, ResultDto.class);

        if(dataRequest.getStatusCode() != HttpStatus.OK) {
//            에러 코드 보내기
//            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        DataArtRouteResponseDto dataResponseDto = (DataArtRouteResponseDto) dataRequest.getBody().getData();

        //3. 파이썬에서 받은 데이터로 response 생성
        ArtRouteResponseDto responseDto = ArtRouteResponseDto.createArtRouteResponseDto(null);

        //4. 레디스에 key는 사용자 id로 임시로 저장

        return responseDto;
    }
}
