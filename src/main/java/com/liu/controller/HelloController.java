package com.liu.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liu
 * @create 2023-10-10-20:50
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    //@PreAuthorize("hasAuthority('system:admin:list')")
    @PreAuthorize("@ex.hasAuthority('system:admin:list')")
    public String hello(Authentication a){
        //springboot 在方法参数中注入Authentication
        return "Hello, "+a.getName();
    }
}
