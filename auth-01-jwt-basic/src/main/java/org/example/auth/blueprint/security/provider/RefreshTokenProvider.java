package org.example.auth.blueprint.security.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class RefreshTokenProvider extends TokenProvider {

    private static final String WEIGHT_KEY = "token-weight";

    public RefreshTokenProvider(String secret, long tokenValidityInSeconds) {
        super(secret, tokenValidityInSeconds);
    }

    // 토큰 생성
    public String createToken(Authentication authentication, Long tokenWeight) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .subject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim(WEIGHT_KEY, tokenWeight)
                .signWith(secretKey)
                .expiration(validity)
                .compact();
    }

    // 토큰에서 가중치를 꺼내 반환한다.
    public long getTokenWeight(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.parseLong(String.valueOf(claims.get(WEIGHT_KEY)));
    }
}
