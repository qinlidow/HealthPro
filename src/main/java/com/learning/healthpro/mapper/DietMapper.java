package com.learning.healthpro.mapper;

import com.learning.healthpro.entity.Diet;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface DietMapper {

    ArrayList<Diet> getDietByUserId(int user_id);

    int insertDietById(Diet diet,int user_id);

    int deleteDietById(int id);

    int setDietById(Diet diet, int id);

}
