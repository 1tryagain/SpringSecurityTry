package com.liu.expression;

import com.liu.domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author liu
 * @create 2023-10-14-18:18
 */
@Component("ex")
public class ExpressionRoot {

    public boolean hasAuthority(String authority){
        //获得当前用户权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        List<String> permissions = loginUser.getPermissions();
        //判断集合中是否包含权限
        return permissions.contains(authority);
    }
}
