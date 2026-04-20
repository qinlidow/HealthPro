package com.learning.healthpro.mapper;

import com.learning.healthpro.entity.Fitness;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface FitnessMapper {

    ArrayList<Fitness> getAllByUserId(int userId);

    Fitness getById(int id);

    int insert(Fitness fitness);

    int update(Fitness fitness);

    int delete(int id);
}
