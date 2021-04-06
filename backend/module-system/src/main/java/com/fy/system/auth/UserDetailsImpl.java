package com.fy.system.auth;

import com.fy.system.model.SysUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class UserDetailsImpl extends User {

    private SysUser sysUser;

    public UserDetailsImpl(SysUser sysUser, Collection<? extends GrantedAuthority> roles) {
        super(sysUser.getAccount(), sysUser.getPassword(), roles);
        this.sysUser = sysUser;
    }

}
