package com.bdos.ssafywiki.user.controller;

import com.bdos.ssafywiki.configuration.jwt.CustomUserDetails;
import com.bdos.ssafywiki.user.dto.UserDto;
import com.bdos.ssafywiki.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 API", description = "마이페이지")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<UserDto.Registration> getUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        System.out.println(userDetails.getUser());


        UserDto.Registration response = userService.checkUserInfo(userDetails.getUser().getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/info")
    public ResponseEntity<String> editUserInfo(@RequestBody UserDto.Registration request,
            @AuthenticationPrincipal CustomUserDetails userDetails
             ) {

        String response = userService.editUser(userDetails.getUser() , request );
        return ResponseEntity.ok(response);
    }

}
