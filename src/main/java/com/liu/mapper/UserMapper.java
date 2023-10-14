package com.liu.mapper;

import com.liu.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liu
 * @create 2023-10-11-22:33
 */
@Mapper
@Repository
public interface UserMapper {
    List<User> getAll();

    User selectUserById(int id);

    User selectUserByName(String username);
}
