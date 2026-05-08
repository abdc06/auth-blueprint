package org.example.auth.blueprint.security.service;

import lombok.RequiredArgsConstructor;
import org.example.auth.blueprint.common.exception.InvalidRefreshTokenException;
import org.example.auth.blueprint.common.model.DataMap;
import org.example.auth.blueprint.security.mapper.AccountMapper;
import org.example.auth.blueprint.security.model.Account;
import org.example.auth.blueprint.security.model.CustomUserDetails;
import org.example.auth.blueprint.security.provider.RefreshTokenProvider;
import org.example.auth.blueprint.security.provider.TokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AccountMapper accountMapper;

    // 아이디와 비밀번호로 사용자를 인증하여 액세스 토큰을 반환한다.
    public DataMap authenticate(String userId, String userPassword) {
        // 받아온 유저네임과 패스워드를 이용해 UsernamePasswordAuthenticationToken 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, userPassword);

        // authenticationToken 객체를 통해 Authentication 객체 생성
        // 이 과정에서 CustomUserDetailsService 에서 우리가 재정의한 loadUserByUsername 메서드 호출
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증 정보를 기준으로 jwt access 토큰 생성
        String accessToken = tokenProvider.createToken(authentication);

        // 위에서 loadUserByUsername를 호출하였으므로 AccountAdapter가 시큐리티 컨텍스트에 저장되어 Account 엔티티 정보를 우리는 알 수 있음
        // 유저 정보에서 중치를 꺼내 리프레시 토큰 가중치에 할당, 나중에 액세스토큰 재발급 시도 시 유저정보 가중치 > 리프레시 토큰이라면 실패
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long tokenWeight = userDetails.getAccount().getTokenWeight();
        String refreshToken = refreshTokenProvider.createToken(authentication, tokenWeight);

        DataMap response = new DataMap();
        DataMap token = new DataMap();
        token.put("accessToken", accessToken);
        token.put("refreshToken", refreshToken);

        response.put("userInfo", userDetails.getAccountInfo());
        response.put("token", token);

        return response;
    }

    @Transactional(readOnly = true)
    public DataMap refreshToken(String refreshToken) {
        // 먼저 리프레시 토큰을 검증한다.
        refreshTokenProvider.validateToken(refreshToken);

        // 리프레시 토큰 값을 이용해 사용자를 꺼낸다.
        // refreshTokenProvider과 TokenProvider는 다른 서명키를 가지고 있기에 refreshTokenProvider를 써야함
        Authentication authentication = refreshTokenProvider.getAuthentication(refreshToken);
        Account account = accountMapper.selectByUserId(authentication.getName());

        // 사용자 디비 값에 있는 것과 가중치 비교, 디비 가중치가 더 크다면 유효하지 않음
        if(account.getTokenWeight() > refreshTokenProvider.getTokenWeight(refreshToken)) throw new InvalidRefreshTokenException();

        // 리프레시 토큰에 담긴 값을 그대로 액세스 토큰 생성에 활용한다.
        String accessToken = tokenProvider.createToken(authentication);
        // 기존 리프레시 토큰과 새로 만든 액세스 토큰을 반환한다.

        DataMap response = new DataMap();
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);

        return response;
    }
}
