package com.fy.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import reactor.core.publisher.Mono;


@Configuration
@EnableR2dbcRepositories
@EnableR2dbcAuditing
public class R2dbcConfig {

    @Bean
    public ReactiveAuditorAware<String> auditorAware(){
     return () -> Mono.just("system");
    }
}
