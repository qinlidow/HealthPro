package com.learning.healthpro.service.impl;

import com.learning.healthpro.entity.Fitness;
import com.learning.healthpro.mapper.FitnessMapper;
import com.learning.healthpro.service.FitnessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FitnessServiceImpl implements FitnessService {

    @Autowired
    private FitnessMapper fitnessMapper;

    @Override
    public ArrayList<Fitness> getAllByUserId(int userId) {
        return fitnessMapper.getAllByUserId(userId);
    }

    @Override
    public Fitness getById(int id) {
        return fitnessMapper.getById(id);
    }

    @Override
    public void add(Fitness fitness) {
        fitnessMapper.insert(fitness);
    }

    @Override
    public void update(Fitness fitness) {
        fitnessMapper.update(fitness);
    }

    @Override
    public void delete(int id) {
        fitnessMapper.delete(id);
    }
}
