package com.fy.system.auth;


import com.fy.system.exception.AuthFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

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
                        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) userDetails;
                        if(passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())){
                            return new AuthenticatedAuthentication(userDetailsImpl.getSysUser(), userDetails.getAuthorities());
                        }
                        throw new AuthFailException();
                    });
        }
        return Mono.empty();
    }
}
