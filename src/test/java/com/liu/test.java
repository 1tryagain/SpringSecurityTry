package com.liu;

import com.liu.domain.User;
import com.liu.mapper.MenuMapper;
import com.liu.mapper.UserMapper;
import com.liu.utils.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author liu
 * @create 2023-10-11-22:39
 */
@SpringBootTest
public class test {
//    @Autowired
//    DataSource dataSource;
//    @Test
//    void contextLoads() throws SQLException {
//        System.out.println(dataSource.getClass());
//        Connection connection = dataSource.getConnection();
//        System.out.println(connection);
//
//        //template模板，拿来即用
//        connection.close();
//    }
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MenuMapper menuMapper;


    @Test
    public void getPoemsList(){
        List<String> strings = menuMapper.selectPermsByUserId((long) 1);
        System.out.println(strings);
    }

    @Test
    public void getUserList(){
        User all = userMapper.selectUserByName("liu");
        System.out.println(all);
    }

    @Test
    public void getPassword(){
        //实现密码加密，数据库存放加密后的密码
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }
//    @Test
//    void testOne() {
//        redisCache.setCacheObject("name","111");
//        String name = (String) redisTemplate.opsForValue().get("name");
//        System.out.println(name); //卷心菜
//    }

}
