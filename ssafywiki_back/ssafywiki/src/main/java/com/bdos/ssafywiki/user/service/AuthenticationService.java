package com.bdos.ssafywiki.user.service;

import com.bdos.ssafywiki.configuration.jwt.JwtTokenProvider;
import com.bdos.ssafywiki.user.dto.UserDto;
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.repository.UserRepository;
import com.bdos.ssafywiki.util.EmailUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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


    public UserDto.Token register(UserDto.Registration request) {

        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .campus(request.getCampus())
                .role(request.getRoll().getKey())
                .build();
        var savedUser = repository.save(user);
        var jwtToken = tokenProvider.createAccessToken(request.getEmail());
        var refreshToken = tokenProvider.createRefreshToken(request.getEmail());
        saveUserToken(savedUser, jwtToken);
        return UserDto.Token.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public UserDto.Token authenticate(UserDto.Login request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = tokenProvider.createAccessToken(request.getEmail());
        var refreshToken = tokenProvider.createRefreshToken(request.getEmail());

        saveUserToken(user, jwtToken);
        return UserDto.Token.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
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
                var accessToken = tokenProvider.createAccessToken(user.getEmail());

                saveUserToken(user, accessToken);
                var authResponse = UserDto.Token.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public boolean checkEmail(UserDto.checkEmail email) {
        // 1. 학생이면 이메일
        if(email.getRole().equals("학생")) return true;
        // 2. 아니면
        String mail = email.getEmail();
        mail = mail.split("@")[1];
        if(mail.equals("multicampus.com")||mail.equals("ssafy.com")) return true;
        return false;
    }

}
