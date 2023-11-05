package com.bdos.ssafywiki.user.controller;

import com.bdos.ssafywiki.configuration.jwt.JwtTokenProvider;
import com.bdos.ssafywiki.user.dto.UserDto;
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 API", description = "마이페이지")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/info")
    public ResponseEntity<UserDto.Registration> getUserInfo(@AuthenticationPrincipal User user) {
        UserDto.Registration response = userService.checkUserInfo(user.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/info")
    public ResponseEntity<String> editUserInfo(@RequestBody UserDto.Registration request,
                                               @AuthenticationPrincipal User user
             ) {

        String response = userService.editUser(user , request );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info/contributeDocs")
    public ResponseEntity<String> getContributeDocs(@RequestBody UserDto.Registration request,
                                                    @AuthenticationPrincipal User user
    ) {

        String response = userService.editUser(user , request );
        return ResponseEntity.ok(response);
    }

}
