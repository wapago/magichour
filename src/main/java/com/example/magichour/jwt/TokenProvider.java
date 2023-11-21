package com.example.magichour.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class TokenProvider implements InitializingBean {
    private Key key;
    private final String secretKey;
    private final long tokenValidityInMillSeconds;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenProvider(@Value("${spring.jwt.secret}") String secretKey,
                         @Value("${spring.jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.secretKey = secretKey;
        this.tokenValidityInMillSeconds = tokenValidityInSeconds * 1000;
    }

    public String createJwt(String userId) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidityInMillSeconds))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

}
