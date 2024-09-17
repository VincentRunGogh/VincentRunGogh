package com.vincentrungogh.domain.route.service;

import com.vincentrungogh.domain.route.service.dto.request.ArtRouteRequestDto;
import com.vincentrungogh.domain.route.service.dto.request.DataSaveRouteRequestDto;
import com.vincentrungogh.domain.route.service.dto.response.DataArtRouteResponseDto;
import com.vincentrungogh.domain.route.service.dto.response.DataSaveRouteResponseDto;
import com.vincentrungogh.global.exception.CustomException;
import com.vincentrungogh.global.exception.ErrorCode;
import com.vincentrungogh.global.util.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class PythonApiService {
    private final RestTemplate restTemplate;
    @Value("${data.api.url}")
    private String dataUrl;

    public DataArtRouteResponseDto convertArtRoute(ArtRouteRequestDto requestDto) {
        URI uri = UriComponentsBuilder
                .fromUriString(dataUrl)
                .path("/api/v1/rootings/art")
                .encode()
                .build()
                .toUri();

        ResponseEntity<ResultDto> response = restTemplate.postForEntity(uri, requestDto, ResultDto.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new CustomException(ErrorCode.FAILED_CHANGE_ROOTING);
        }

        return (DataArtRouteResponseDto) response.getBody().getData();
    }

    public DataSaveRouteResponseDto saveRoute(DataSaveRouteRequestDto requestDto) {
        URI uri = UriComponentsBuilder
                .fromUriString(dataUrl)
                .path("/api/v1/rootings")
                .encode()
                .build()
                .toUri();

        ResponseEntity<ResultDto> response = restTemplate.postForEntity(uri, requestDto, ResultDto.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new CustomException(ErrorCode.FAILED_SAVE_ROOTING);
        }

        return (DataSaveRouteResponseDto) response.getBody().getData();
    }
}

