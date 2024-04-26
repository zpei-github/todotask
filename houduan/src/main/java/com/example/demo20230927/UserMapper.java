package com.example.demo20230927;

import org.apache.ibatis.annotations.Mapper;

/*Mapper一般用于与数据库交互，通过接口调用Mybatis下的xml文件实现数据库操作
*
*
* */

@Mapper
public interface UserMapper {
    void insertUser(User user);
    User findByUsername(String username);
}
