package com.learning.healthpro.service;

import com.learning.healthpro.entity.Body;

import java.util.ArrayList;

public interface BodyService {

    public ArrayList<Body> getBody(int user_id);

    public void addBody(Body body,int user_id);

    public void deleteBody(int id);

    public void setBody(Body body,int id);
}
