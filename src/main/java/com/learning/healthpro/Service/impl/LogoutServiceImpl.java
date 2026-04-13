package com.learning.healthpro.service.impl;

import com.learning.healthpro.service.LogoutService;
import org.springframework.stereotype.Service;

@Service
public class LogoutServiceImpl implements LogoutService {

    @Override
    public String logout() {
        return "登出成功，请前端清除本地Token";
    }
}
