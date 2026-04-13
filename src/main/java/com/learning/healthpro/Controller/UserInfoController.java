package com.learning.healthpro.controller;

import com.learning.healthpro.common.Result;
import com.learning.healthpro.entity.User;
import com.learning.healthpro.service.UserInfoService;
import com.learning.healthpro.service.impl.UpdateUserInfoImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UpdateUserInfoImpl updateUserInfo;

    @GetMapping("/GetInfo")
    public Result getInfo(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        if(userId == null){
            return Result.error("未获取到用户信息");
        }
        return Result.success(userInfoService.getInfo(userId));
    }

    @PostMapping("/UpdateInfo")
    public Result upUserInfo(@RequestBody User user, HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        updateUserInfo.updateUserInfo(user, userId);
        return Result.success();
    }

    @PostMapping("/UpdatePassword")
    public Result updatePassword(@RequestBody String password, HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        updateUserInfo.updatePassword(password, userId);
        return Result.success();
    }
}
