package com.fy.system.auth;

import com.fy.system.exception.BaseException;
import com.fy.system.repository.r2dbc.SysUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ReactiveUserDetailServiceImpl implements ReactiveUserDetailsService {

    private final SysUserRepository sysUserRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return sysUserRepository.findFirstByAccount(username)
                .switchIfEmpty(Mono.error(new BaseException("用户/密码不正确", HttpStatus.UNAUTHORIZED.value())))
                .map(sysUser -> User
                        .withUsername(sysUser.getAccount())
                        .password(sysUser.getPassword())
                        .build());
    }

}
