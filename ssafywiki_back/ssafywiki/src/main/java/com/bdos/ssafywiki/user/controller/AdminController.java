package com.bdos.ssafywiki.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// admin 유저 확인용
@Controller
@RequestMapping("/admin")
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
