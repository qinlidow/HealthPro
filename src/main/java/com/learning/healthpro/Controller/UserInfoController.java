package com.learning.healthpro.controller;

import com.learning.healthpro.common.Result;
import com.learning.healthpro.context.ConcurrentContext;
import com.learning.healthpro.entity.User;
import com.learning.healthpro.service.UserInfoService;
import com.learning.healthpro.service.impl.UpdateUserInfoImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserInfoController {

    private final UserInfoService userInfoService;
    private final UpdateUserInfoImpl updateUserInfo;

    public UserInfoController(UserInfoService userInfoService,
                              UpdateUserInfoImpl updateUserInfo){
        this.userInfoService = userInfoService;
        this.updateUserInfo = updateUserInfo;
    }

    @GetMapping("/GetInfo")
    public Result getInfo(){
        Integer userId = ConcurrentContext.get();
        if(userId == null){
            return Result.error("未获取到用户信息");
        }
        return Result.success(userInfoService.getInfo(userId));
    }

    @PostMapping("/UpdateInfo")
    public Result upUserInfo(@RequestBody User user){
        Integer userId = ConcurrentContext.get();
        updateUserInfo.updateUserInfo(user, userId);
        return Result.success();
    }

    @PostMapping("/UpdatePassword")
    public Result updatePassword(@RequestBody String password){
        Integer userId = ConcurrentContext.get();
        updateUserInfo.updatePassword(password, userId);
        return Result.success();
    }
}
