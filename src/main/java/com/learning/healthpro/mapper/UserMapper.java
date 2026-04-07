package com.learning.healthpro.mapper;

import com.learning.healthpro.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;


@Mapper
public interface UserMapper  {

    @Select("select password from user where phone = #{id}")
    Optional<String> findPassWordByPhone(String phone);

    @Select("select count(u) from user u where u.phone = #{phone}")
    Long countByPhone(String phone);

    @Select("select id from user where phone = #{phone}")
    int findIdByPhone(String phone);

    @Select("select name,phone,email,age,gender from user where id = #{id}")
    User selectById(int id);

    @Insert("insert into user(name,password,phone,email) values(#{name},#{password},#{phone},#{email})")
    int insert(User user);

}


