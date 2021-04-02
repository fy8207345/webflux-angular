package com.fy.system.auth;

import com.fy.system.exception.AuthFailException;
import com.fy.system.repository.r2dbc.SysUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

import java.util.Collections;

@RequiredArgsConstructor
public class ReactiveUserDetailServiceImpl implements ReactiveUserDetailsService {

    private final SysUserRepository sysUserRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return sysUserRepository.findFirstByAccount(username)
                .switchIfEmpty(Mono.error(new AuthFailException()))
                .map(sysUser -> User
                        .withUsername(sysUser.getAccount())
                        .password(sysUser.getPassword())
                        .authorities(Collections.emptyList())
                        .build());
    }

}
