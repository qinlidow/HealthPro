package com.learning.healthpro.service.impl;

import com.learning.healthpro.entity.User;

import com.learning.healthpro.mapper.UserMapper;
import com.learning.healthpro.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserMapper userMapper;

    public boolean isRegistered(User user){
        boolean result = false;
        Long count = userMapper.countByPhone(user.getPhone());
        if(count > 0){
            result = true;
        }
        return result;
    }

    public void register(User user){
        userMapper.insert(user);
    }
}

