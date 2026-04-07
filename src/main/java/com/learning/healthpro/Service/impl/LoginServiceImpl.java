package com.learning.healthpro.service.impl;

import com.learning.healthpro.entity.User;
import com.learning.healthpro.mapper.UserMapper;
import com.learning.healthpro.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    public boolean LoginCheck(User user){
        boolean result = false;
        String password = userMapper.findPassWordByPhone(user.getPhone()).orElse(null);
        if (user.getPassword().equals(password)){
            result = true;
        }
        return  result;
    }
}
