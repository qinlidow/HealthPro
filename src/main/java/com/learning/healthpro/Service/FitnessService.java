package com.learning.healthpro.service;

import com.learning.healthpro.entity.Fitness;

import java.util.ArrayList;

public interface FitnessService {

    ArrayList<Fitness> getAllByUserId(int userId);

    Fitness getById(int id);

    void add(Fitness fitness);

    void update(Fitness fitness);

    void delete(int id);
}
