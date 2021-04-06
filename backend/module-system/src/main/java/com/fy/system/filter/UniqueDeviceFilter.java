package com.fy.system.filter;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UniqueDeviceFilter implements WebFilter {

    public static final String UNIQUE_DEVICE_HEAD_NAME = "UDID";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String uniqueDviceId = getUniqueDviceId(exchange.getRequest());
        if(!StringUtils.hasText(uniqueDviceId)){
            uniqueDviceId = UUID.randomUUID().toString();
            exchange = exchange.mutate()
                    .request(exchange.getRequest()
                            .mutate().header(UNIQUE_DEVICE_HEAD_NAME, uniqueDviceId)
                            .build())
            .build();
            exchange.getResponse()
                    .addCookie(ResponseCookie.fromClientResponse(UNIQUE_DEVICE_HEAD_NAME, uniqueDviceId).build());
        }
        return chain.filter(exchange);
    }

    public static String getUniqueDviceId(ServerHttpRequest request){
        HttpHeaders requestHeaders = request.getHeaders();
        return requestHeaders.getFirst(UNIQUE_DEVICE_HEAD_NAME);
    }
}
