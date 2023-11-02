package com.bdos.ssafywiki.user.controller;

import com.bdos.ssafywiki.discussion.dto.DiscussionDto;
import com.bdos.ssafywiki.user.dto.UserDto;
import com.bdos.ssafywiki.user.service.AuthenticationService;
import com.bdos.ssafywiki.util.EmailUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

//로그인 회원가입 관련 권한이 필요 없는 Controller
@Tag(name = "회원 API", description = "로그인, 회원가입")
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final EmailUtil emailService;
    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<UserDto.UserToken> register(@RequestBody UserDto.Registration request) {
        return ResponseEntity.ok(service.signup(request));
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<Authentication> authenticate(@RequestBody UserDto.Login request) {
        return ResponseEntity.ok(service.login(request));
    }


    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request,HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }

    @Operation(summary = "이메일 인증번호 전송")
    @PostMapping("/email")
    public ResponseEntity<String> checkEmail(@RequestBody UserDto.checkEmail email) {

        int result = emailService.sendEmail(email.getEmail());

        if(result == 1) return ResponseEntity.ok("성공");
        else return ResponseEntity.ok("실패");
    }

    @Operation(summary = "이메일 인증번호 확인")
    @PostMapping("/email/auth")
    public ResponseEntity<String> checkEmailAuth(@RequestBody UserDto.checkEmail email) {

        int result = emailService.authEmail(email.getEmail() , email.getAuthCode());

        if(result == 1) return ResponseEntity.ok("성공");
        else return ResponseEntity.ok("실패");
    }



}
