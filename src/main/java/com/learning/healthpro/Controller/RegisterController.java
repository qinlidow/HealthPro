package com.learning.healthpro.Controller;

import com.learning.healthpro.Service.RegisterService;
import com.learning.healthpro.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    public boolean register(User user){
        boolean result = false;
        if (registerService.isRegistered(user)){
            return result;
        }

        else {
            registerService.register(user);
            result = true;
            return result;
        }
    }



}
