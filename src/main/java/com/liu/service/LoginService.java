package com.liu.service;

import com.liu.domain.User;
import com.liu.utils.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @author liu
 * @create 2023-10-12-21:22
 */
public interface LoginService {
    //登入
    public ResponseResult login(User user);
    //退出登入
    public ResponseResult loginOut();
}
