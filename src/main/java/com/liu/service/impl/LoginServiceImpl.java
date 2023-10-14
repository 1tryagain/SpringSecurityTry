package com.liu.service.impl;

import com.liu.domain.LoginUser;
import com.liu.domain.User;
import com.liu.mapper.UserMapper;
import com.liu.service.LoginService;
import com.liu.utils.JwtUtil;
import com.liu.utils.RedisCache;
import com.liu.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author liu
 * @create 2023-10-12-21:25
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseResult login(User user) {
        //AuthenticationManager
        //把用户名和密码封装成authenticationToken对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //认证通过，使用userid生成一个jwt getPrincipal返回一个UserDetailsService的LoginUser类型
         LoginUser loginUser= (LoginUser)authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //将用户信息存入redis
        redisCache.setCacheObject("login:"+userId,loginUser);
        HashMap<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return new ResponseResult(200,"登陆成功",map);
    }

    @Override
    public ResponseResult loginOut() {
        //从SecurityContextHolder获得userid
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userid = loginUser.getUser().getId().toString();
        redisCache.deleteObject("login:"+userid);
        return new ResponseResult(200,"退出成功");
    }
}
