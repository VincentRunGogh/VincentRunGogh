package com.vincentrungogh.global.service;

import com.vincentrungogh.domain.route.service.dto.common.Position;
import com.vincentrungogh.global.config.RedisConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public void saveRoutePositionList(int userId, List<Position> positionList) {
        String key = "rooting:" + userId;
        redisTemplate.opsForValue().set(key, positionList);
    }

    public void removeRoutePositionList(int userId){
        String key = "rooting:" + userId;
        redisTemplate.delete(key);
    }

    public List<Position> getRoutePositionList(int userId) {
        String key = "rooting:" + userId;
        return (List<Position>) redisTemplate.opsForValue().get(key);
    }

    public void saveEmailCode(String email, String code,  String expirationTime){

        redisTemplate.opsForValue().set(email, code);

        redisTemplate.opsForValue().set(email+ "-expirationTime", expirationTime);
    }

    public String getEmailCode(String email){
        return (String) redisTemplate.opsForValue().get(email);
    }

    public String getEmailExpirationTime(String email){
        String key = email + "-expirationTime";
        return (String) redisTemplate.opsForValue().get(key);
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
