package com.fy.system.auth;

import com.fy.system.model.SysUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@ToString
public class AuthenticatedAuthentication extends UsernamePasswordAuthenticationToken {

    private SysUser sysUser;

    public AuthenticatedAuthentication(SysUser sysUser, Collection<? extends GrantedAuthority> roles) {
        super(sysUser.getAccount(), sysUser.getPassword(), roles);
        this.sysUser = sysUser;
    }

}
