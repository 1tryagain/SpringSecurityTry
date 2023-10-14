package com.liu.controller;

import com.liu.domain.User;
import com.liu.service.LoginService;
import com.liu.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liu
 * @create 2023-10-12-21:22
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        return loginService.login(user);
    }

    @GetMapping("/user/logout")
    public ResponseResult loginOut(){
        return loginService.loginOut();
    }
}
