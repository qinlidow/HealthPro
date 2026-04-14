package com.learning.healthpro.mapper;

import com.learning.healthpro.entity.Body;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface BodyMapper {

    ArrayList<Body> getBodyByUserId(int user_id);

    int deleteBodyById(int id);

    int insertBodyById(Body body,int user_id);

    int setBodyById(Body body,int id);
}
