package com.learning.healthpro.controller;

import com.learning.healthpro.common.Result;
import com.learning.healthpro.service.RegisterService;
import com.learning.healthpro.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/Register")
    public Result register(@RequestBody User user){
        if (registerService.isRegistered(user)){
            System.out.println("手机号已被注册!");
            return Result.error("手机号已被注册");
        }

        else {
            registerService.register(user);
            System.out.println("账号注册成功!");
            return Result.success();
        }
    }



}
