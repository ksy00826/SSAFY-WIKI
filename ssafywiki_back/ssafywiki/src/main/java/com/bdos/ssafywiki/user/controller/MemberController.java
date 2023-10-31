package com.bdos.ssafywiki.user.controller;

import com.bdos.ssafywiki.user.dto.UserDto;
import com.bdos.ssafywiki.user.entity.User;
import com.bdos.ssafywiki.user.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "로그인", description = "로그인 확인 후 토큰을 반환")
    @PostMapping("/login")
    public ResponseEntity<UserDto.Token> loginUser(@RequestBody UserDto.Login loginuser){
        UserDto.Token token = memberService.loginUser(loginuser);

        return ResponseEntity.ok(token);
    }


    @Operation(summary = "회원가입", description = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<String> SingUpUser(@RequestBody UserDto.Registration regist){
        User u = memberService.registerUser(regist);
        if(u != null) return ResponseEntity.ok("완료");
        else return ResponseEntity.ok("실패");
    }
}
