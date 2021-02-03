package com.fy.system.jwt;

import com.fy.system.properties.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "roles";
    @Autowired
    private JwtProperties jwtProperties;

    private SecretKey secretKey;

    @PostConstruct
    protected void init(){
        String s = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
        secretKey = Keys.hmacShaKeyFor(s.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(Authentication authentication){
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(AUTHORITIES_KEY, authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));

        Date date = new Date();
        Date validity = new Date(date.getTime() + jwtProperties.getValidityInMills());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        List<? extends GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(claims.get(AUTHORITIES_KEY).toString());
        User principal = new User(claims.getSubject(), "", grantedAuthorities);
        return new UsernamePasswordAuthenticationToken(principal, token, grantedAuthorities);
    }

    public boolean validateToken(String token){
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        }catch (JwtException | IllegalArgumentException e){
            log.info("Invalid JWT token : {}", token);
            log.trace("Invalid JWT token trace.", e);
        }
        return false;
    }

    public String refreshToken(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        Date date = new Date();
        Date validity = new Date(date.getTime() + jwtProperties.getValidityInMills());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
