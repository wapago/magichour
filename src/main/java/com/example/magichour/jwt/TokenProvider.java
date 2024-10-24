package com.example.magichour.jwt;

import com.example.magichour.dto.member.TokenDto;
import com.example.magichour.entity.member.RefreshToken;
import com.example.magichour.repository.RefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Log4j2
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "auth";
    private static Key secretKey;
    private final long accessTokenValidityInMillSeconds;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenProvider(@Value("${spring.jwt.secret}") String secret,
                         @Value("${spring.jwt.token-validity-in-seconds}") long accessTokenValidityInMillSeconds,
                         RefreshTokenRepository refreshTokenRepository) {
        byte[] keyBytes = Decoders.BASE64URL.decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenValidityInMillSeconds = accessTokenValidityInMillSeconds;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public TokenDto createJwt(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long refreshTokenValidityInMillSeconds = (1000 * 60 * 60 * 24 * 7);
        String accessToken = generateToken(authentication, authorities, accessTokenValidityInMillSeconds);
        String refreshToken = generateToken(authentication, authorities, refreshTokenValidityInMillSeconds);

        refreshTokenRepository.save(RefreshToken.builder().refreshToken(refreshToken).userId(authentication.getName()).expired(false).build());

        return TokenDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    public String generateToken(Authentication authentication, String authorities, long expirationTimeInMillis) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInMillis))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if(claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, accessToken, authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 서명입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
    }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
