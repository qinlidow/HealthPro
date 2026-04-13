package com.learning.healthpro.mapper;

import com.learning.healthpro.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Optional;


@Mapper
public interface UserMapper  {

    Optional<String> findPassWordByPhone(String phone);

    Long countByPhone(String phone);

    int findIdByPhone(String phone);

    User getInfoById(int id);

    int insert(User user);

    int UpdateUserInfo(User user,int id);

    int UpdatePassWord(String password,int id);
}


