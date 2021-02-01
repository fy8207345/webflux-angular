package com.fy.system.utils;

import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

public class TokenUtils {

    public static final String TOKEN_HEADER_NAME = "token";

    public static String getToken(ServerHttpRequest request){
        HttpHeaders requestHeaders = request.getHeaders();
        MultiValueMap<String, HttpCookie> requestCookies = request.getCookies();
        String token = requestHeaders.getFirst(TOKEN_HEADER_NAME);
        if(!ObjectUtils.isEmpty(token)){
            return token;
        }
        if(requestCookies.containsKey(TOKEN_HEADER_NAME)){
            List<HttpCookie> httpCookies = requestCookies.get(TOKEN_HEADER_NAME);
            Optional<HttpCookie> any = httpCookies.stream()
                    .filter(httpCookie -> httpCookie.getName().equals(TOKEN_HEADER_NAME))
                    .findAny();
            if(any.isPresent()){
                return any.get().getValue();
            }
        }
        return null;
    }
}
