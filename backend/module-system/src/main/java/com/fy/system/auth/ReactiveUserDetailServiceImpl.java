package com.fy.system.auth;

import com.fy.system.exception.AuthFailException;
import com.fy.system.repository.r2dbc.SysUserRepository;
import com.fy.system.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class ReactiveUserDetailServiceImpl implements ReactiveUserDetailsService {

    private final SysUserRepository sysUserRepository;
    private final SysRoleService sysRoleService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return sysUserRepository.findFirstByAccount(username)
                .switchIfEmpty(Mono.error(new AuthFailException()))
                .flatMap(sysUser -> sysRoleService.findByUserId(sysUser.getId())
                        .collectList()
                        .map(sysRoles -> new UserDetailsImpl(sysUser, sysRoles)));
    }

}
