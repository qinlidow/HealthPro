package com.learning.healthpro.service;

import com.learning.healthpro.entity.User;
import org.springframework.web.bind.annotation.RequestBody;

public interface UpdateUserInfo {
    public void updateUserInfo(@RequestBody User user);

    public void updatePassword(String password,int id);
}
