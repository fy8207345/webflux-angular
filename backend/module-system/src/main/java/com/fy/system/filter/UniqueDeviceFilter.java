package com.fy.system.filter;

import com.google.common.collect.Lists;
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

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

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
                    .getHeaders()
                    .put(UNIQUE_DEVICE_HEAD_NAME, Lists.newArrayList(uniqueDviceId));
        }
        return chain.filter(exchange).checkpoint("observed error", true);
    }

    public static String getUniqueDviceId(ServerHttpRequest request){
        HttpHeaders requestHeaders = request.getHeaders();
        return requestHeaders.getFirst(UNIQUE_DEVICE_HEAD_NAME);
    }
}
