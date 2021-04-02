package com.fy.system.auth;


import com.fy.system.exception.AuthFailException;
import com.fy.system.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class ReactiveAuthenticationManagerImpl implements ReactiveAuthenticationManager {

    private final ReactiveUserDetailsService reactiveUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        if(authentication instanceof UsernamePasswordAuthenticationToken){
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) authentication;
            return reactiveUserDetailsService.findByUsername(usernamePasswordAuthenticationToken.getPrincipal().toString())
                    .map(userDetails -> {
                        if(passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())){
                            Collection<GrantedAuthority> authorityCollection = new ArrayList<>();
                            return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), authorityCollection);
                        }
                        throw new AuthFailException();
                    });
        }
        return Mono.empty();
    }
}
