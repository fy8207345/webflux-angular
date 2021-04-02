package com.fy.system.config;

import com.fy.system.auth.ReactiveAuthenticationManagerImpl;
import com.fy.system.auth.ReactiveUserDetailServiceImpl;
import com.fy.system.controller.SysCaptchaController;
import com.fy.system.controller.SysLoginController;
import com.fy.system.filter.JwtTokenAuthenticationFilter;
import com.fy.system.jwt.JwtTokenProvider;
import com.fy.system.repository.r2dbc.SysUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity, JwtTokenProvider jwtTokenProvider){
        return httpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(it -> it.pathMatchers(SysCaptchaController.MAPPING_PATH + "/**").permitAll()
                        .pathMatchers(SysLoginController.MAPPING_PATH + "/**").permitAll()
                        .pathMatchers("/**")
                        .authenticated())
                .addFilterAt(new JwtTokenAuthenticationFilter(jwtTokenProvider), SecurityWebFiltersOrder.HTTP_BASIC)
                .build();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        return new ReactiveAuthenticationManagerImpl(userDetailsService, passwordEncoder);
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService(SysUserRepository sysUserRepository){
        return new ReactiveUserDetailServiceImpl(sysUserRepository);
    }
}
