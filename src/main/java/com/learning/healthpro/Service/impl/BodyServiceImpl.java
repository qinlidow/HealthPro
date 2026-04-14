package com.learning.healthpro.service.impl;

import com.learning.healthpro.entity.Body;
import com.learning.healthpro.mapper.BodyMapper;
import com.learning.healthpro.service.BodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BodyServiceImpl implements BodyService {

    @Autowired
    private BodyMapper bodyMapper;
    public ArrayList<Body> getBody(int user_id){
        return bodyMapper.getBodyByUserId(user_id);
    }

    public void addBody(Body body,int user_id){
        bodyMapper.insertBodyById(body, user_id);
    }

    public void deleteBody(int id){
        bodyMapper.deleteBodyById(id);
    }

    public void setBody(Body body,int id){
        bodyMapper.setBodyById(body, id);
    }
}
