package com.learning.healthpro.service.impl;

import com.learning.healthpro.entity.User;
import com.learning.healthpro.mapper.UserMapper;
import com.learning.healthpro.service.LoginService;
import com.learning.healthpro.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    public String LoginCheck(User user){
        String password = userMapper.findPassWordByPhone(user.getPhone()).orElse(null);
        if (password != null && user.getPassword().equals(password)){
            int userId = userMapper.findIdByPhone(user.getPhone());
            return jwtUtil.generateToken(userId, user.getPhone());
        }
        return null;
    }
}
