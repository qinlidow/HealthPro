package com.learning.healthpro.controller;

import com.learning.healthpro.common.Result;
import com.learning.healthpro.context.ConcurrentContext;
import com.learning.healthpro.entity.Sleep;
import com.learning.healthpro.service.SleepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequestMapping("/Sleep")
@RestController
public class SleepController {

    @Autowired
    private SleepService sleepService;

    @GetMapping("/GetSleep")
    public Result getSleep() {
        Integer userId = ConcurrentContext.get();
        if (userId == null) {
            return Result.error("未找到用户信息");
        }
        ArrayList<Sleep> list = sleepService.getAllByUserId(userId);
        return Result.success(list);
    }

    @GetMapping("/GetSleepById/{id}")
    public Result getSleepById(@PathVariable int id) {
        Sleep sleep = sleepService.getById(id);
        if (sleep == null) {
            return Result.error("记录不存在");
        }
        return Result.success(sleep);
    }

    @PostMapping("/AddSleep")
    public Result addSleep(@RequestBody Sleep sleep) {
        Integer userId = ConcurrentContext.get();
        if (userId == null) {
            return Result.error("未找到用户信息");
        }
        sleep.setUser_id(userId);
        sleepService.add(sleep);
        return Result.success();
    }

    @PostMapping("/SetSleep/{id}")
    public Result updateSleep(@PathVariable int id, @RequestBody Sleep sleep) {
        Sleep existing = sleepService.getById(id);
        if (existing == null) {
            return Result.error("记录不存在");
        }
        sleep.setId(id);
        sleep.setUser_id(existing.getUser_id());
        sleepService.update(sleep);
        return Result.success();
    }

    @DeleteMapping("/DeleteSleep/{id}")
    public Result deleteSleep(@PathVariable int id) {
        sleepService.delete(id);
        return Result.success();
    }
}
