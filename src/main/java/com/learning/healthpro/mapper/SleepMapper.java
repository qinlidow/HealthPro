package com.learning.healthpro.mapper;

import com.learning.healthpro.entity.Sleep;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface SleepMapper {

    ArrayList<Sleep> getAllByUserId(int userId);

    Sleep getById(int id);

    int insert(Sleep sleep);

    int update(Sleep sleep);

    int delete(int id);
}
