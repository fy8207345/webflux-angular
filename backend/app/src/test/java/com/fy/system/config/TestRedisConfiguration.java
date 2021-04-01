package com.fy.system.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.TestConfiguration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@TestConfiguration
@AutoConfigureBefore(RedisConfig.class)
public class TestRedisConfiguration {

    private final RedisProperties redisProperties;

    private RedisServer redisServer;

    public TestRedisConfiguration(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
        this.redisServer = new RedisServer(redisProperties.getPort());
    }

    @PostConstruct
    public void init(){
//        redisServer.start();
    }

    @PreDestroy
    public void destroy(){
//        redisServer.stop();
    }
}
