package com.learning.healthpro.service.impl;

import com.learning.healthpro.common.Result;
import com.learning.healthpro.entity.User;
import com.learning.healthpro.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UpdateUserInfoImpl {

    @Autowired
    private UserMapper userMapper;

    public void updateUserInfo(User user,int id){
        userMapper.UpdateUserInfo(user,id);//后续id需要改成获取当前的登录用户
    }

    public void updatePassword(String password,int id){
        userMapper.UpdatePassWord(password,id);//后续id需要改成获取当前的登录用户
    }
}
