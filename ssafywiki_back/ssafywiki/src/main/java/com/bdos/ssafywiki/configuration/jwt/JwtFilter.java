package com.bdos.ssafywiki.configuration.jwt;

import com.bdos.ssafywiki.exception.BusinessLogicException;
import com.bdos.ssafywiki.exception.ExceptionCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 헤더(Authorization) 에 있는 토큰을 꺼내 이상이 없는 경우 SecurityContext에 저장
 * Request 이전에 작동
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {


    private final JwtTokenProvider jwtTokenProvider;
    private static final String TOKEN_PREFIX = "Bearer ";

    /**
     * doFilterInternal 함수 오버라이드
     * 필터링 로직 수행
     * request header에서 token을 꺼내고 유효성 검사 후 유저 정보를 꺼내 Security Context 에 저장
     * SecurityConfig 에 인증을 설정한 API에 대한 request 요청은 모두 이 필터를 거치기 때문에 토큰 정보가 없거나
     * 유효하지 않은 경우 정상적으로 수행되지 않음
     */


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        log.info("d왜안들어와!!!!!!!!!!!!!!!!!!!!!!!!!!!! >>> ");

        String token = jwtTokenProvider.resolveToken(request);

        log.info(token);
        // 토큰 없는 경우 로직 종료
        if(token == null || !token.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Bearer 제거
        token = token.replace(TOKEN_PREFIX, "");
        if(token.equals("undefined")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 사용자 인증
        log.info("doFilter >>> " + token);
        try {
            if (jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
                log.info("[Token 인증] 사용자 인증 성공"+auth);
            }
        }  catch (Exception e) {
            e.printStackTrace();
//            throw new BaseException(INVALID_JWT);
//            throw new IllegalArgumentException("토큰 잘못 됨");
            log.info("[Token 인증] 유효하지 않은 토큰입니다.");

            throw new BusinessLogicException(ExceptionCode.INVALID_ACCESS_TOKEN);
        }
        filterChain.doFilter(request, response);
    }

}
