package com.bdos.ssafywiki.user.service;

import com.bdos.ssafywiki.configuration.jwt.JwtTokenProvider;
import com.bdos.ssafywiki.exception.BusinessLogicException;
import com.bdos.ssafywiki.exception.ExceptionCode;
import com.bdos.ssafywiki.user.dto.UserDto;
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.enums.Role;
import com.bdos.ssafywiki.user.repository.UserRepository;
import com.bdos.ssafywiki.util.EmailUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;


    public UserDto.UserToken signup(UserDto.Registration request) {
        // 1. 이미 존재하는 아이디인지 확인
        Optional<User> find = repository.findByEmail(request.getEmail());
        if (!find.isEmpty()) throw new BusinessLogicException(ExceptionCode.MEMBER_EXIST);

        // 2. User entity 생성
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .campus(request.getCampus())
                .nickname(request.getNickname())
                .number(request.getNumber())
                .role(Role.krToRole(request.getRole()))
                .build();

        // 3. 저장
        var savedUser = repository.save(user);
        System.out.println(savedUser);

        // 4. access Token, RefreshToken 생성 및 저장
        var jwtToken = tokenProvider.createAccessToken(savedUser);
        var refreshToken = tokenProvider.createRefreshToken(savedUser);
        saveUserToken(savedUser, jwtToken);

        // 반환
        return UserDto.UserToken.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .name(user.getName())
                .build();
    }

    public Authentication login(UserDto.Login request) {
        System.out.println(request);
        // 1. id, pass를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        // 2. User Password 인증
        //"authenticate" 가 실행될때 "CustomUserDetailService.loadUserByUsername" 실행
        try {
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            return authenticate;

//            // token 생성
//            String accessToken = tokenProvider.generateAccessToken(authentication);
//            String refreshToken = tokenProvider.generateRefreshToken(authentication);
//            User user = (User) authentication.getPrincipal(); // user 정보


        } catch (UsernameNotFoundException e) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        } catch (BadCredentialsException e) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }
//        catch (LockedException e) {
//            throw new BusinessLogicException(ExceptionCode.BLOCKED_USER);
//        }

    }

    private void saveUserToken(User user, String jwtToken) {
        user.setRefreshToken(jwtToken);
        repository.save(user);
    }


    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = tokenProvider.getUserEmail(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (tokenProvider.validateToken(refreshToken)) {
                var accessToken = tokenProvider.createAccessToken(user);

                saveUserToken(user, accessToken);
                var authResponse = UserDto.UserToken.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

}
