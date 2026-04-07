package com.learning.healthpro.service;

import com.learning.healthpro.entity.User;

public interface RegisterService {

    public boolean isRegistered(User user);

    public void register(User user);
}
