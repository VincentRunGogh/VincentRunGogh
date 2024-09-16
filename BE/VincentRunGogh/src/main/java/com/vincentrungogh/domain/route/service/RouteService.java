package com.vincentrungogh.domain.route.service;

import com.vincentrungogh.domain.myhealth.entity.MyHealth;
import com.vincentrungogh.domain.myhealth.repository.MyHealthRepository;
import com.vincentrungogh.domain.route.entity.Route;
import com.vincentrungogh.domain.route.repository.RouteRepository;
import com.vincentrungogh.domain.route.service.dto.common.Position;
import com.vincentrungogh.domain.route.service.dto.request.ArtRouteRequestDto;
import com.vincentrungogh.domain.route.service.dto.request.DataSaveRouteRequestDto;
import com.vincentrungogh.domain.route.service.dto.request.SaveRouteRequestDto;
import com.vincentrungogh.domain.route.service.dto.response.DataSaveRouteResponseDto;
import com.vincentrungogh.domain.route.service.dto.response.SaveRouteResponseDto;
import com.vincentrungogh.domain.route.service.dto.response.DataArtRouteResponseDto;
import com.vincentrungogh.domain.route.service.dto.response.ArtRouteResponseDto;
import com.vincentrungogh.domain.user.entity.User;
import com.vincentrungogh.global.exception.CustomException;
import com.vincentrungogh.global.exception.ErrorCode;
import com.vincentrungogh.global.util.ResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
//@PropertySource("classpath:application.properties")
public class RouteService {
    private final RouteRepository routeRepository;
    private final MyHealthRepository myHealthRepository;
//    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${data.api.url}")
    private String dataUrl;

    private final String ROOTING_PREFIX = "rooting:";

    @Transactional
    public ArtRouteResponseDto convertArtRoute(/*@AuthenticationPrincipal UserPrincipal principal,*/ArtRouteRequestDto requestDto) {
        //1. 사용자 확인
        User user = null;
//        redisTemplate.opsForValue().set("1", requestDto.getPositionList());
//
//        List<Position> list = (List<Position>) redisTemplate.opsForValue().get("1");
//
//        log.info(" "+list.toString());

        //2. request를 토대로 파이썬으로 보내기
        URI uri = UriComponentsBuilder
                .fromUriString(dataUrl)
//                .fromUriString("http://j11b307a.p.ssafy.io:8000")
                .path("/api/v1/rootings/art")
                .encode()
                .build()
                .toUri();

        //파이썬으로 post 요청 보내기
        ResponseEntity<ResultDto> dataResponse = restTemplate.postForEntity(uri, requestDto, ResultDto.class);

        if(dataResponse.getStatusCode() != HttpStatus.OK) {
//            에러 코드 보내기
            throw new CustomException(ErrorCode.FAILED_CHANGE_ROOTING);
        }

        DataArtRouteResponseDto dataResponseDto = (DataArtRouteResponseDto) dataResponse.getBody().getData();

        //3. 파이썬에서 받은 데이터로 response 생성
        ArtRouteResponseDto responseDto = ArtRouteResponseDto.createArtRouteResponseDto(dataResponseDto.getPositionList());

        //4. 레디스에 key는 사용자 id로 임시로 저장
        String key = ROOTING_PREFIX+user.getId();
        redisTemplate.opsForValue().set(key, requestDto.getPositionList());

        return responseDto;
    }

    @Transactional
    public SaveRouteResponseDto saveRoute(/*@AuthenticationPrincipal UserPrincipal principal,*/SaveRouteRequestDto requestDto) {
        //1. 사용자 확인
        User user = null;

        //2. aws로 이미지 저장

        //3. 루트엔티티 생성
        Route route = Route.createRoute(user, requestDto.getTitle(), requestDto.getArtImage());

        //4. 저장
        routeRepository.save(route);

        //5. routeId 조회
        int routeId = route.getId();

        //6. 레디스에서 루트 좌표들 가져옴
        String key = ROOTING_PREFIX+user.getId();
        List<Position> positionList = (List<Position>) redisTemplate.opsForValue().get(key);
        DataSaveRouteRequestDto dataRequestDto = DataSaveRouteRequestDto.createDataSaveRouteRequestDto(routeId, positionList);

        //7. request를 토대로 파이썬으로 보내기
        URI uri = UriComponentsBuilder
                .fromUriString(dataUrl)
                .path("/api/v1/rootings")
                .encode()
                .build()
                .toUri();

        //8. 파이썬으로 post 요청 보내기
        ResponseEntity<ResultDto> dataResponse = restTemplate.postForEntity(uri, dataRequestDto, ResultDto.class);

        if(dataResponse.getStatusCode() != HttpStatus.OK) {
//            에러 코드 보내기
            throw new CustomException(ErrorCode.FAILED_SAVE_ROOTING);
        }

        //9. 받은 것을 토대로 업데이트 하기
        DataSaveRouteResponseDto dataSaveResponse = (DataSaveRouteResponseDto) dataResponse.getBody().getData();
        route.updateCenter(dataSaveResponse.getCenterLat(), dataSaveResponse.getCenterLng(), dataSaveResponse.getDistance());
        routeRepository.save(route);

        //10. response 생성
        MyHealth myHealth = myHealthRepository.findByUser(user).orElseThrow(
                //유저가 존재하지 않음
//                () -> new CustomException(ErrorCode.FAILED_SAVE_ROOTING)
        );
        double averageSpeed = myHealth.getAverageSpeed();
        // averageSpeed 이거 0되지 않게 해야함
        return SaveRouteResponseDto.createSaveRouteResponseDto(route, averageSpeed);
    }
}
