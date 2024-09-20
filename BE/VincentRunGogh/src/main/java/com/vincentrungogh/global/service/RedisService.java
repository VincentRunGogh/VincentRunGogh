package com.vincentrungogh.global.service;

import com.vincentrungogh.domain.route.service.dto.common.Position;
import com.vincentrungogh.global.config.RedisConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final String ROOTING_PREFIX = "rooting:";

    public void saveRoutePositionList(int userId, List<Position> positionList) {
        String key = ROOTING_PREFIX + userId;
        redisTemplate.opsForValue().set(key, positionList);
    }

    public List<Position> getRoutePositionList(int userId) {
        String key = ROOTING_PREFIX + userId;
        return (List<Position>) redisTemplate.opsForValue().get(key);
    }

    public void saveEmailCode(String email, String code){
        long timeout = 5;
        TimeUnit timeUnit = TimeUnit.MINUTES;
        redisTemplate.opsForValue().set(email, code, timeout, timeUnit);
    }

    public void saveRefreshToken(int userId, String refreshToken){
        String key = "refreshToken:" + userId;
        redisTemplate.opsForValue().set(key, refreshToken);
    }

    public String getRefreshToken(int userId){
        String key = "refreshToken:" + userId;
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void removeRefreshToken(int userId){
        String key = "refreshToken:" + userId;
        redisTemplate.delete(key);
    }
}
