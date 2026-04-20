package com.learning.healthpro.service;

import com.learning.healthpro.entity.Sleep;

import java.util.ArrayList;

public interface SleepService {

    ArrayList<Sleep> getAllByUserId(int userId);

    Sleep getById(int id);

    void add(Sleep sleep);

    void update(Sleep sleep);

    void delete(int id);
}
