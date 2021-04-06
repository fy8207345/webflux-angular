package com.fy.system.service;

import com.fy.common.model.ApiResult;
import com.fy.system.exception.BaseException;
import com.fy.system.filter.UniqueDeviceFilter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.Duration;

@RequiredArgsConstructor
@Service
public class CaptchaService {

    public static final String HASH = "captcha";

    private final @NonNull ReactiveStringRedisTemplate reactiveStringRedisTemplate;
    private ReactiveValueOperations<String, String> valueOperations;

    @PostConstruct
    public void init(){
        valueOperations = reactiveStringRedisTemplate.opsForValue();
    }

    public Mono<String> save(ServerWebExchange exchange, String captcha){
        String uniqueDviceId = UniqueDeviceFilter.getUniqueDviceId(exchange.getRequest());
        return valueOperations.set(uniqueDviceId, captcha, Duration.ofSeconds(60))
                .flatMap(aBoolean -> {
                    if(aBoolean != null && aBoolean){
                        return Mono.just(uniqueDviceId);
                    }
                    return Mono.error(new BaseException("生成验证码失败", ApiResult.DEFAULT_ERROR));
                });
    }

    public Mono<String> get(ServerWebExchange exchange){
        String uniqueDviceId = UniqueDeviceFilter.getUniqueDviceId(exchange.getRequest());
        return valueOperations.get(uniqueDviceId)
                .flatMap(code -> valueOperations.delete(uniqueDviceId)
                        .map(aBoolean -> code));
    }
}
