package com.fy.system.controller;

import com.fy.common.model.ApiResult;
import com.fy.system.exception.BaseException;
import com.fy.system.jwt.JwtTokenProvider;
import com.fy.system.model.LoginForm;
import com.fy.system.properties.CaptchaProperties;
import com.fy.system.service.CaptchaService;
import io.jsonwebtoken.lang.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(SysLoginController.MAPPING_PATH)
public class SysLoginController {

    public static final String MAPPING_PATH = "/system/login";

    private final JwtTokenProvider jwtTokenProvider;
    private final ReactiveAuthenticationManager authenticationManager;
    private final CaptchaService captchaService;
    private final CaptchaProperties captchaProperties;

    @PostMapping
    public Mono<ApiResult<?>> login(@RequestBody Mono<LoginForm> authRequest, ServerWebExchange exchange){
        return authRequest
                .flatMap(loginForm -> {
                    if(captchaProperties.isEnabled()){
                        return captchaService.get(exchange)
                                .switchIfEmpty(Mono.error(new BaseException("验证码已经失效", ApiResult.ERROR_CAPTCHA)))
                                .flatMap(s -> {
                                    if(s.equals(loginForm.getCode())){
                                        return Mono.just(loginForm);
                                    }
                                    return Mono.error(new BaseException("验证码错误", ApiResult.ERROR_CAPTCHA));
                                });
                    }
                    return Mono.just(loginForm);
                })
            .flatMap(login -> authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())))
            .map(jwtTokenProvider::createToken)
            .map(jwt -> ApiResult.success(Maps.of("token", jwt).build()));
    }
}
