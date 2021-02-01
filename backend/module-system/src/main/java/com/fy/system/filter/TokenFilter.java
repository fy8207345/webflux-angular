package com.fy.system.filter;

import com.fy.system.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.Serializable;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class TokenFilter implements WebFilter {


    @Autowired
    private TokenGenerator tokenGenerator;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        String requestToken = TokenUtils.getToken(serverWebExchange.getRequest());
        if(ObjectUtils.isEmpty(requestToken)){
            ServerHttpResponse response = serverWebExchange.getResponse();
            HttpHeaders responseHeaders = response.getHeaders();
            Serializable token = tokenGenerator.generate();
            responseHeaders.set(TokenUtils.TOKEN_HEADER_NAME, token.toString());
            serverWebExchange.getRequest().mutate().headers(httpHeaders -> httpHeaders.set(TokenUtils.TOKEN_HEADER_NAME, token.toString()));
            response.addCookie(ResponseCookie.from(TokenUtils.TOKEN_HEADER_NAME, token.toString()).httpOnly(true).build());
        }
        return webFilterChain.filter(serverWebExchange);
    }
}
