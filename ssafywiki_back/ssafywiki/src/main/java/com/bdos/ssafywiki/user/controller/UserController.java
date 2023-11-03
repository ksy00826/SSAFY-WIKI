package com.bdos.ssafywiki.user.controller;

import com.bdos.ssafywiki.revision.dto.RevisionDto;
import com.bdos.ssafywiki.user.dto.UserDto;
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.repository.UserRepository;
import com.bdos.ssafywiki.user.service.AuthenticationService;
import com.bdos.ssafywiki.user.service.UserService;
import com.bdos.ssafywiki.util.EmailUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Security;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "유저 API", description = "마이페이지")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<User> getUserInfo(Authentication authentication) {
        System.out.println(authentication.getName());
        System.out.println(authentication.getDetails());
        User response = userService.searchUser(authentication.getName()).get();
        return ResponseEntity.ok(response);
    }

}
