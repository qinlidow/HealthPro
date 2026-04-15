package com.learning.healthpro.service;

import com.learning.healthpro.entity.Diet;

import java.util.ArrayList;

public interface DietService {
    public ArrayList<Diet> getDiet(int userid);

    public void addDiet(Diet diet,int userid);

    public void deleteDiet(int id);

    public void setDiet(Diet diet,int userid);
}

