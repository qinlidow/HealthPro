package com.learning.healthpro.service.impl;

import com.learning.healthpro.context.ConcurrentContext;
import com.learning.healthpro.entity.User;
import com.learning.healthpro.mapper.UserMapper;
import com.learning.healthpro.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserMapper userMapper;

    public User getInfo(int id){
        Integer i = ConcurrentContext.get();
        return userMapper.getInfoById(id);
    }
}
