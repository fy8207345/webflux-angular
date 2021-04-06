package com.fy.system.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class JwtToken {

    private String token;

    private Date expireTime;
}
