package com.learning.healthpro.Controller;

import com.learning.healthpro.Service.LoginService;
import com.learning.healthpro.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/Login")
    public boolean Login(@RequestBody User user){
        boolean result = loginService.LoginCheck(user);
        return result;
    }
}
