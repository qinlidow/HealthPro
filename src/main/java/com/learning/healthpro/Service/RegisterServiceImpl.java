package com.learning.healthpro.Service;

import com.learning.healthpro.dao.User;
import com.learning.healthpro.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserRepository userRepository;

    public boolean isRegistered(User user){
        boolean result = false;
        Long count = userRepository.countByPhone(user.getPhone());
        if(count > 0){
            result = true;
        }
        return result;
    }

    public void register(User user){
        userRepository.save(user);
    }
}

