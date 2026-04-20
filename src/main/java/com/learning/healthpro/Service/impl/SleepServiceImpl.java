package com.learning.healthpro.service.impl;

import com.learning.healthpro.entity.Sleep;
import com.learning.healthpro.mapper.SleepMapper;
import com.learning.healthpro.service.SleepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SleepServiceImpl implements SleepService {

    @Autowired
    private SleepMapper sleepMapper;

    @Override
    public ArrayList<Sleep> getAllByUserId(int userId) {
        return sleepMapper.getAllByUserId(userId);
    }

    @Override
    public Sleep getById(int id) {
        return sleepMapper.getById(id);
    }

    @Override
    public void add(Sleep sleep) {
        sleepMapper.insert(sleep);
    }

    @Override
    public void update(Sleep sleep) {
        sleepMapper.update(sleep);
    }

    @Override
    public void delete(int id) {
        sleepMapper.delete(id);
    }
}
