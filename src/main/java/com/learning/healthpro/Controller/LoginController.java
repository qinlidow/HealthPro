package com.learning.healthpro.controller;

import com.learning.healthpro.common.Result;
import com.learning.healthpro.service.LoginService;
import com.learning.healthpro.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/Login")
    public Result Login(@RequestBody User user){
        boolean isright = loginService.LoginCheck(user);
        if(isright == true){
            System.out.println("登陆成功");
            return Result.success();
        }
        else{
            System.out.println("登录失败");
            return Result.error("账号不存在或密码不正确");
        }
    }
}
