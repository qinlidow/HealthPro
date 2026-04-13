package com.learning.healthpro.controller;

import com.learning.healthpro.common.Result;
import com.learning.healthpro.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {

    @Autowired
    private LogoutService logoutService;

    @PostMapping("/Logout")
    public Result logout() {
        return Result.success(logoutService.logout());
    }
}
