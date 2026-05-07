package org.example.auth.blueprint.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.auth.blueprint.security.provider.TokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String[] whitelist = {"/api/auth/token", "/api/auth/refreshToken", "/login"};
    private final TokenProvider tokenProvider;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        //API 경로가 아닌 경우 필터 제외
        if (!path.startsWith("/api")) {
            return true;
        }

        //나머지 화이트리스트 체크
        return PatternMatchUtils.simpleMatch(whitelist, path);
    }

    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        //헤더 형식 확인
        if(authorizationHeader == null){
            SecurityContextHolder.getContext().setAuthentication(null);
            filterChain.doFilter(request, response);
            return;
        }

        String token = "";
        if (authorizationHeader.startsWith("Bearer ")){
            token = authorizationHeader.replace("Bearer ", "");
        } else{
            log.error("Authorization 헤더 형식이 틀립니다. : {}", authorizationHeader);
            filterChain.doFilter(request, response);
            return;
        }

        // 유효성 검증
        if (tokenProvider.validateToken(token)) {
            // 토큰에서 유저네임, 권한을 뽑아 스프링 시큐리티 유저를 만들어 Authentication 반환
            Authentication authentication = tokenProvider.getAuthentication(token);
            // 해당 스프링 시큐리티 유저를 시큐리티 건텍스트에 저장, 즉 디비를 거치지 않음
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        } else {
            log.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
        }

        filterChain.doFilter(request, response);
    }
}