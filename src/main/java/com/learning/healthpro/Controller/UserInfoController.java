package com.learning.healthpro.controller;

import com.learning.healthpro.common.Result;
import com.learning.healthpro.entity.User;
import com.learning.healthpro.service.UserInfoService;
import com.learning.healthpro.service.impl.UpdateUserInfoImpl;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UpdateUserInfoImpl updateUserInfo;

    @GetMapping("/GetInfo/{id}")
    public Result getInfo(@PathVariable Integer id){
        if(id == null){
            return Result.error("id参数不能为空");
        }
        return Result.success(userInfoService.getInfo(id));
    }

    @PostMapping("/UpdateInfo")
    public Result upUserInfo(@RequestBody User user){
        updateUserInfo.updateUserInfo(user, 1);
        return Result.success();//后续加形参id
    }

    @PostMapping("/UpdatePassword")
    public Result updatePassword(@RequestBody String password){
        System.out.println("password");
        updateUserInfo.updatePassword("12892",2);
        return Result.success();//后续加形参id
    }
}
