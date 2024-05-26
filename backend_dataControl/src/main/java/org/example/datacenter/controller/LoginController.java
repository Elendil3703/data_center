package org.example.datacenter.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@RestController

public class LoginController {
    //这在正式的程序里没有用，只是为了便于后端实现登录后跳转首页
    @GetMapping("/home")
    public String hello() {
        return "Home Page";
    }
}
