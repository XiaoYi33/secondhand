package com.example.springboot.mapper;

import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * author HKX
 * date: 2024-01-30 16:20
 * description:
 **/

@Mapper
public interface UserMapper {
    @Insert("insert into `user` (username,password,name,phone,email,address,avatar) " +
            "values (#{username},#{password},#{name},#{phone},#{email},#{address},#{avatar})")
    void insert(User user);

    @Update("update `user` set username = #{username}, password = #{password}, name= #{name}, phone = #{phone}, " +
            "email = #{email}, address = #{address}, avatar = #{avatar} where id = #{id}")
    void update(User user);

    @Delete("delete from `user` where id = #{id}")
    void delete(Integer id);

    @Select("select * from `user`")
    List<User> selectAll();

    @Select("select * from `user` where id = #{id}")
    User selectById(Integer id);

    @Select("select * from `user` where username = #{username}")
    User selectByUsername(String username);

    @Select("select * from `user` where username=#{username} and name=#{name}")
    List<User> selectByMore(@Param("username") String username, @Param("name") String name);

    @Select("select * from `user` where name like concat('%',#{name},'%') ")
    List<User> selectLike( @Param("name") String name);

    @Select("select * from `user` where name like concat('%',#{name},'%') limit #{skipNumber},#{pageSize}")

    List<User> selectByPage(@Param("name") String name,@Param("skipNumber") Integer  skipNumber,@Param("pageSize") Integer pageSize);

    @Select("select count(id) from `user` where name like concat('%',#{name},'%')")
    Integer selectCountByPage(@Param("name") String name);


}
