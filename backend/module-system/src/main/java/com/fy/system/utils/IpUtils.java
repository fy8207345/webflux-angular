package com.fy.system.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

import java.net.InetSocketAddress;

/**
 * 获取IP方法
 *
 * @author ruoyi
 */
public class IpUtils {
    public static String getIpAddr(ServerHttpRequest request) {
        if (request == null) {
            return "unknown";
        }
        HttpHeaders requestHeaders = request.getHeaders();
        String ip = requestHeaders.getFirst("x-forwarded-for");
        if (!StringUtils.hasText(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = requestHeaders.getFirst("Proxy-Client-IP");
        }
        if (!StringUtils.hasText(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = requestHeaders.getFirst("X-Forwarded-For");
        }
        if (!StringUtils.hasText(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = requestHeaders.getFirst("WL-Proxy-Client-IP");
        }
        if (!StringUtils.hasText(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = requestHeaders.getFirst("X-Real-IP");
        }
        if (!StringUtils.hasText(ip) || "unknown".equalsIgnoreCase(ip)) {
            InetSocketAddress remoteAddress = request.getRemoteAddress();
            if(remoteAddress != null){
                ip = remoteAddress.getHostString();
            }
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}
