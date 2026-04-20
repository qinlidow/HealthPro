package com.learning.healthpro.mapper;

import com.learning.healthpro.entity.CheckIn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface CheckInMapper {

    int insertCheckIn(CheckIn checkIn);

    CheckIn getCheckIn(@Param("userId") int userId, @Param("date") String date);

    ArrayList<CheckIn> getCheckInList(int userId);

    ArrayList<CheckIn> getCheckInByMonth(@Param("userId") int userId, @Param("yearMonth") String yearMonth);

    int getCheckInCount(int userId);
}
