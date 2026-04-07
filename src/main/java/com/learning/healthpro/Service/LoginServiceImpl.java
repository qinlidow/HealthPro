package com.learning.healthpro.Service;

import com.learning.healthpro.dao.User;
import com.learning.healthpro.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    private UserRepository userRepository;

    public boolean LoginCheck(User user){
        boolean result = false;
        String password = userRepository.findPassWordByPhone(user.getPhone()).orElse(null);
        if (user.getPassword().equals(password)){
            result = true;
        }
        return  result;
    }
}
