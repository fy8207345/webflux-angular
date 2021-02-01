package com.fy.system.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.Duration;

@RequiredArgsConstructor
@Service
public class CaptchaService {

    private final @NonNull ReactiveStringRedisTemplate reactiveStringRedisTemplate;
    private ReactiveValueOperations<String, String> valueOperations;

    @PostConstruct
    public void init(){
        valueOperations = reactiveStringRedisTemplate.opsForValue();
    }

    public Mono<Boolean> save(String token, String captcha){
        return valueOperations
                .set(token, captcha, Duration.ofSeconds(60));
    }

    public Mono<String> get(String token){
        return valueOperations.get(token);
    }
}
