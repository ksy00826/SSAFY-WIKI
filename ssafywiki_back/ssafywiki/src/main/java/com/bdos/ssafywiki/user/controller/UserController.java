package com.bdos.ssafywiki.user.controller;

import com.bdos.ssafywiki.user.dto.UserDto;
import com.bdos.ssafywiki.user.service.AuthenticationService;
import com.bdos.ssafywiki.util.EmailUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "유저 API", description = "마이페이지")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService service;
    private final EmailUtil emailService;
    @GetMapping({"/", ""})
    public String loginForm(){
        return "loginForm";
    }

}
