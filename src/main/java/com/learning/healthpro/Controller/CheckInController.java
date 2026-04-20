package com.learning.healthpro.controller;

import com.learning.healthpro.common.Result;
import com.learning.healthpro.context.ConcurrentContext;
import com.learning.healthpro.entity.CheckIn;
import com.learning.healthpro.service.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequestMapping("/CheckIn")
@RestController
public class CheckInController {

    @Autowired
    private CheckInService checkInService;

    @PostMapping("/Do")
    public Result doCheckIn(@RequestBody CheckIn checkIn) {
        Integer userId = ConcurrentContext.get();
        if (userId == null) {
            return Result.error("未找到用户信息");
        }
        // 只允许打卡当天
        String today = java.time.LocalDate.now().toString();
        if (!today.equals(checkIn.getDate())) {
            return Result.error("只能打卡当天");
        }
        checkInService.checkIn(userId, checkIn.getDate());
        return Result.success();
    }

    @GetMapping("/IsChecked")
    public Result isChecked(@RequestParam String date) {
        Integer userId = ConcurrentContext.get();
        if (userId == null) {
            return Result.error("未找到用户信息");
        }
        boolean checked = checkInService.isCheckedIn(userId, date);
        return Result.success(checked);
    }

    @GetMapping("/List")
    public Result getCheckInList() {
        Integer userId = ConcurrentContext.get();
        if (userId == null) {
            return Result.error("未找到用户信息");
        }
        ArrayList<CheckIn> list = checkInService.getCheckInList(userId);
        return Result.success(list);
    }

    @GetMapping("/ByMonth")
    public Result getCheckInByMonth(@RequestParam String yearMonth) {
        Integer userId = ConcurrentContext.get();
        if (userId == null) {
            return Result.error("未找到用户信息");
        }
        ArrayList<CheckIn> list = checkInService.getCheckInByMonth(userId, yearMonth);
        return Result.success(list);
    }

    @GetMapping("/Count")
    public Result getCheckInCount() {
        Integer userId = ConcurrentContext.get();
        if (userId == null) {
            return Result.error("未找到用户信息");
        }
        int count = checkInService.getCheckInCount(userId);
        return Result.success(count);
    }

    @GetMapping("/Streak")
    public Result getStreak() {
        Integer userId = ConcurrentContext.get();
        if (userId == null) {
            return Result.error("未找到用户信息");
        }
        int streak = checkInService.getStreak(userId);
        return Result.success(streak);
    }
}
