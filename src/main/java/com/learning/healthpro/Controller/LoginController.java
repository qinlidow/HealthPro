package com.learning.healthpro.controller;

import com.learning.healthpro.common.Result;
import com.learning.healthpro.service.LoginService;
import com.learning.healthpro.entity.User;
import com.learning.healthpro.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/Login")
    public Result Login(@RequestBody User user){
        String token = loginService.LoginCheck(user);
        if(token != null){
            int id = jwtUtil.getUserIdFromToken(token);
            System.out.println(id);
            return Result.success(token);

        }
        else{
            return Result.error("账号不存在或密码不正确");
        }
    }


}
