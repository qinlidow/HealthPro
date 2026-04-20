package com.learning.healthpro.service;

import com.learning.healthpro.entity.CheckIn;

import java.util.ArrayList;

public interface CheckInService {

    void checkIn(int userId, String date);

    boolean isCheckedIn(int userId, String date);

    ArrayList<CheckIn> getCheckInList(int userId);

    ArrayList<CheckIn> getCheckInByMonth(int userId, String yearMonth);

    int getCheckInCount(int userId);

    int getStreak(int userId);
}
