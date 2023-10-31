package com.bdos.ssafywiki.user.controller;

import com.bdos.ssafywiki.user.dto.UserDto;
import com.bdos.ssafywiki.user.service.AuthenticationService;
import com.bdos.ssafywiki.util.EmailUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

//로그인 회원가입 관련 권한이 필요 없는 Controller
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    private final EmailUtil emailService;

    @PostMapping("/register")
    public ResponseEntity<UserDto.Token> register(@RequestBody UserDto.Registration request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserDto.Token> authenticate(@RequestBody UserDto.Login request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request,HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }

    @PostMapping("/email")
    public ResponseEntity<Boolean> checkEmail(@RequestBody UserDto.checkEmail email) {
        boolean result = service.checkEmail(email);
        if(result) emailService.sendEmail(email.getEmail());

        return ResponseEntity.ok(result);
    }

}
