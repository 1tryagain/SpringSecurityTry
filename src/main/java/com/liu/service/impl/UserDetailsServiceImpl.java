package com.liu.service.impl;

import com.liu.domain.LoginUser;
import com.liu.domain.User;
import com.liu.mapper.MenuMapper;
import com.liu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liu
 * @create 2023-10-12-20:19
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
   @Autowired
    private UserMapper userMapper;
   @Autowired
   private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectUserByName(username);
        if (user==null){
            throw new RuntimeException("用户名或密码错误");
        }
        //查询相应权限信息
        List<String> list = menuMapper.selectPermsByUserId(user.getId());
        //封装成UserDetails对象返回
        return new LoginUser(user,list);
    }
}
