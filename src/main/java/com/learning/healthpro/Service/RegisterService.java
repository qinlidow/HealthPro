package com.learning.healthpro.Service;

import com.learning.healthpro.dao.User;

public interface RegisterService {

    public boolean isRegistered(User user);

    public void register(User user);
}
