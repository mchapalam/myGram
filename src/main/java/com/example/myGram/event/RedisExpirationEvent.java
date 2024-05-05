package com.example.myGram.event;

import com.example.myGram.exception.RefreshTokenException;
import com.example.myGram.model.entity.RefreshToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisExpirationEvent {

    @EventListener
    public void handlerRedisKeyExpiredEvent(RedisKeyExpiredEvent<RefreshToken> event){
        RefreshToken refreshToken = (RefreshToken) event.getValue();

        if(refreshToken == null){
            throw new RefreshTokenException("Error very bad");
        }
        log.info("Refresh token with");
    }
}
