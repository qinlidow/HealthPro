package com.learning.healthpro.service.impl;

import com.learning.healthpro.entity.CheckIn;
import com.learning.healthpro.mapper.CheckInMapper;
import com.learning.healthpro.service.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
public class CheckInServiceImpl implements CheckInService {

    @Autowired
    private CheckInMapper checkInMapper;

    @Override
    public void checkIn(int userId, String date) {
        CheckIn existing = checkInMapper.getCheckIn(userId, date);
        if (existing == null) {
            CheckIn checkIn = CheckIn.builder()
                    .user_id(userId)
                    .date(date)
                    .build();
            checkInMapper.insertCheckIn(checkIn);
        }
    }

    @Override
    public boolean isCheckedIn(int userId, String date) {
        return checkInMapper.getCheckIn(userId, date) != null;
    }

    @Override
    public ArrayList<CheckIn> getCheckInList(int userId) {
        return checkInMapper.getCheckInList(userId);
    }

    @Override
    public ArrayList<CheckIn> getCheckInByMonth(int userId, String yearMonth) {
        return checkInMapper.getCheckInByMonth(userId, yearMonth);
    }

    @Override
    public int getCheckInCount(int userId) {
        return checkInMapper.getCheckInCount(userId);
    }

    @Override
    public int getStreak(int userId) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();
        int streak = 0;
        LocalDate day = today;
        while (true) {
            String dateStr = day.format(fmt);
            if (checkInMapper.getCheckIn(userId, dateStr) != null) {
                streak++;
                day = day.minusDays(1);
            } else {
                break;
            }
        }
        return streak;
    }
}
