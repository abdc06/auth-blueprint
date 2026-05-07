package org.example.auth.blueprint.security.config;

import org.example.auth.blueprint.security.provider.RefreshTokenProvider;
import org.example.auth.blueprint.security.provider.TokenProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;

@Controller
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {

    // 액세스 토큰 발급용, 리프레시 토큰 발급용은 각각 별도의 키와 유효기간을 갖는다.
    @Primary
    @Bean(name = "tokenProvider")
    public TokenProvider tokenProvider(JwtProperties jwtProperties) {
        return new TokenProvider(jwtProperties.getSecret(), jwtProperties.getAccessTokenValidityInSeconds());
    }

    // 리프레시 토큰은 별도의 키를 가지기 떄문에 리프레시 토큰으로는 API 호출 불가
    // 액세스 토큰 재발급 시 검증용
    @Bean(name = "refreshTokenProvider")
    public RefreshTokenProvider refreshTokenProvider(JwtProperties jwtProperties) {
        return new RefreshTokenProvider(jwtProperties.getRefreshTokenSecret(), jwtProperties.getRefreshTokenValidityInSeconds());
    }
}
