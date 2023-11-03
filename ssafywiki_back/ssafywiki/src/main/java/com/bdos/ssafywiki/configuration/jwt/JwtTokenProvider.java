package com.bdos.ssafywiki.configuration.jwt;

import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.repository.UserRepository;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    /**
     * JwtTokenProvider : 유저 정보로 jwt access/refresh 토큰 생성 및 재발급 + 토큰으로부터 유저 정보 받기
     */


    @Value("${jwt.secretKey}")
    private String secretKey;
    @Value("${jwt.refreshExpiration}")
    private long refreshExpirationTime;
    @Value("${jwt.expiration}")
    private long expirationTime;

//    @Value("Authorization")
//    private String jwtHeader;


    private final UserRepository userRepository;
    private final CustomUserDetailsService userDetailsService;


    /**
     * === createAccessToken , createRefreshToken ===
     * 유저 정보를 넘겨받아 토큰 생성
     * 넘겨받은 authentication의 getName() 메소드를 통해 username 가져옴 (username : User의 num 필드로 설정함)
     * 각각 expiration time 설정
     * */


    /**
     * Access 토큰 생성
     */

    public String createAccessToken(User user) {

        log.info("엑세스 토큰 진입 직후 >>> " + user.getId());
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expirationTime);

        System.out.println("create access >>> " + expireDate);

        log.info("엑세스 토큰 클레임 생성 후 >>> " + user.getId());

        return Jwts.builder()
                .setSubject(user.getName())
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }

    /**
     * Refresh 토큰 생성
     */
    public String createRefreshToken(User user) {
        log.info("리프레시 토큰 진입 직후 >>> " + user.getId());
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + refreshExpirationTime);

        log.info("리프레시 토큰 클레임 생성 후 >>> " + user.getId());

        String refreshToken = Jwts.builder()
                .setSubject(user.getName())
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        log.info("1111111111");

        // 디비에 저장
//        User user = userRepository.findByEmail(email).get();
        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return refreshToken;
    }

    /**
     * ==== getAuthentication ====
     * 토큰을 복호화해 토큰에 들어있는 유저 정보 꺼냄
     * 이후 authentication 객체 반환
     * */

    /**
     * 토큰으로부터 클레임을 만들고, 이를 통해 User 객체 생성해 Authentication 객체 반환
     */
    public Authentication getAuthentication(String token) {

        String userPrincipal = Jwts.parser().
                setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody().getSubject();

        System.out.println("userPrincipal >>> " + userPrincipal);

        UserDetails userDetails = userDetailsService.loadUserByUsername(userPrincipal);

        System.out.println("userDetails >>> " + userDetails);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * resolveToken
     * http 헤더로부터 bearer 토큰을 가져옴.
     */
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer")) {
            System.out.println(bearerToken);
            return bearerToken;
        }
        return null;
    }

    /**
     * resolveToken
     * http 헤더로부터 bearer 토큰을 가져옴.
     */
    public String resolveRefreshToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("refreshToken");

        System.out.println("resolveRefreshToken >>> " + bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            System.out.println(bearerToken);
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * validateToken
     * 토큰 정보 검증
     * Jwts 모듈이 각각 상황에 맞는 exception 던져줌
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (JwtException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 토큰으로 회원 정보 조회
     */
    public String getUserEmail(String token) {

        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // 엑세스 토큰 헤더 설정
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("Authorization", "Bearer " + accessToken);
    }

    // 리프레시 토큰 헤더 설정
    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader("refreshToken", "Bearer " + refreshToken);
    }

    // get authentication by user email
    public Authentication getAuthenticationByUsername(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public User getUserByToken(String token) {
        String email = this.getUserEmail(token.substring(7));
        return userDetailsService.loadUserByUsername(email);
    }

}
