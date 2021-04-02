package com.fy.system.controller;

import com.fy.system.filter.JwtTokenAuthenticationFilter;
import com.fy.system.jwt.JwtTokenProvider;
import com.fy.system.model.LoginForm;
import com.fy.system.model.SysUser;
import io.jsonwebtoken.lang.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(SysLoginController.MAPPING_PATH)
public class SysLoginController {

    public static final String MAPPING_PATH = "/system/login";

    private final JwtTokenProvider jwtTokenProvider;
    private final ReactiveAuthenticationManager authenticationManager;

    @PostMapping
    public Mono<ResponseEntity<?>> login(@RequestBody Mono<LoginForm> authRequest){
        return authRequest
                .delayElement(Duration.ofSeconds(5))
            .flatMap(login -> authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())))
            .map(jwtTokenProvider::createToken)
            .map(jwt -> {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add(HttpHeaders.AUTHORIZATION, JwtTokenAuthenticationFilter.HEADER_PREFIX + jwt);
                Map<String, String> result = Maps.of("token", jwt).build();
                return new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
            });
    }
}
