package com.jihan.vehicle.server.vehicleserver.dao;

import com.jihan.vehicle.server.vehicleserver.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    // 查询用户信息
    @Select("select * from users where username = #{username} and password = #{password} ")
    User authUser(@Param("username") String username,@Param("password") String password);

    @Select("select * from users where username = #{username}")
    User getUser(@Param("username") String username);

    @Select("select * from users where username = #{username}")
    Integer exist(@Param("username") String username);

    // 查询所有用户信息
    @Select("SELECT * FROM users")
    List<User> findAll();

    // 根据id查询用户信息
    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(@Param("id") int id);

    // 添加用户信息
    @Insert("INSERT INTO users(username, password) VALUES(#{username}, #{password})")
    Integer addUser(@Param("username") String username, @Param("password") String password);

    // 更新用户信息
    @Update("UPDATE users SET username=#{username}, password=#{password} WHERE id=#{id}")
    Integer updateUser(@Param("id") int id, @Param("username") String username, @Param("password") String password);

    // 删除用户信息
    @Delete("DELETE FROM users WHERE id=#{id}")
    Integer deleteUser(@Param("id") int id);
}
