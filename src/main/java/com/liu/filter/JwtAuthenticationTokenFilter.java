package com.liu.filter;

import com.liu.domain.LoginUser;
import com.liu.utils.JwtUtil;
import com.liu.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author liu
 * @create 2023-10-13-15:01
 */
//过滤器配置好，要再UsernamePasswordAuthenticationFilter前面，则取SecurityConfig中注入
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
     //获取token
        String token = request.getHeader("token");
        if(!StringUtils.hasText(token)){
            //放行
            filterChain.doFilter(request,response);
            //return 是当后面的filter返回时不用继续执行
            return;
        }
        //解析token
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        //从redis中获取用户信息
        String redisKey="login:"+userid;
        LoginUser loginUser=redisCache.getCacheObject(redisKey);
        if(Objects.isNull(loginUser)){
            throw new RuntimeException("用户未登录");
        }
        //获取权限信息封装到Authentication中
        //存入SecurityContextHolder 之后的filter都是从存入SecurityContextHolder获得信息
        //用三个参数是因为 有super.setAuthenticated(true); 不用再检查权限
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
