package com.bdos.ssafywiki.user.controller;

import com.bdos.ssafywiki.user.dto.UserDto;
import com.bdos.ssafywiki.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/email")
    public ResponseEntity<?> checkEmail(@RequestBody String email) {
        return ResponseEntity.ok("able");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDto.Registration registrationDto) {
        userService.registerUser(registrationDto);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDto.Login loginDto) {
        userService.loginUser(loginDto);
        return ResponseEntity.ok("User registered successfully");
    }
}
