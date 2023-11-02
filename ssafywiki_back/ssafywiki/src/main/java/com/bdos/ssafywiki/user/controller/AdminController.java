package com.bdos.ssafywiki.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// admin 유저 확인용
@Tag(name = "ADMIN API", description = "ADMIN 권한")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("/superadmin")
    public String superadmin(){
        return "superadmin";
    }

    @GetMapping("/accessDenied")
    public String accessDenied(){
        return "accessDenied";
    }
}
