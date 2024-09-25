package com.vincentrungogh.domain.running.handler;

import com.vincentrungogh.global.auth.service.JwtService;
import com.vincentrungogh.global.exception.CustomException;
import com.vincentrungogh.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final JwtService jwtService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        // 1. 토큰 존재 여부 확인
        if(accessor.getCommand() == StompCommand.CONNECT){
            if(jwtService.validateToken(accessor.getFirstNativeHeader("Authorization"))){
                throw new CustomException(ErrorCode.USER_NOT_FOUND);
            }
        }

        return message;
    }

    @EventListener(SessionConnectedEvent.class)
    public void onApplicationEvent(SessionConnectedEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String accessToken = accessor.getFirstNativeHeader("Authorization");

        if(!jwtService.validateToken(accessToken)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        accessor.getSessionAttributes().put("userId", jwtService.getUserId(accessToken));
    }

}
