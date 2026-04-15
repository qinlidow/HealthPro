package com.learning.healthpro.service.impl;

import com.learning.healthpro.context.ConcurrentContext;
import com.learning.healthpro.entity.Diet;
import com.learning.healthpro.mapper.DietMapper;
import com.learning.healthpro.service.DietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DietServicImpl implements DietService {

    @Autowired
    private DietMapper dietMapper;

    public ArrayList<Diet> getDiet(int userid){
        return dietMapper.getDietByUserId(userid);
    }

    public void addDiet(Diet diet,int userid){
        dietMapper.insertDietById(diet,userid);
    }

    public void deleteDiet(int id){
        dietMapper.deleteDietById(id);
    }

    public void setDiet(Diet diet,int id){
        dietMapper.setDietById(diet,id);
    }
}
