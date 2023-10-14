package com.liu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liu
 * @create 2023-10-13-21:02
 */
@Mapper
@Repository
public interface MenuMapper {
    List<String> selectPermsByUserId(Long id);
}
