package com.learning.healthpro.controller;

import com.learning.healthpro.service.RegisterService;
import com.learning.healthpro.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/Register")
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
