package com.learning.healthpro.controller;

import com.learning.healthpro.common.Result;
import com.learning.healthpro.context.ConcurrentContext;
import com.learning.healthpro.entity.Fitness;
import com.learning.healthpro.service.FitnessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequestMapping("/Fitness")
@RestController
public class FitnessController {

    @Autowired
    private FitnessService fitnessService;

    @GetMapping("/GetFitness")
    public Result getFitness() {
        Integer userId = ConcurrentContext.get();
        if (userId == null) {
            return Result.error("未找到用户信息");
        }
        ArrayList<Fitness> list = fitnessService.getAllByUserId(userId);
        return Result.success(list);
    }

    @GetMapping("/GetFitnessById/{id}")
    public Result getFitnessById(@PathVariable int id) {
        Fitness fitness = fitnessService.getById(id);
        if (fitness == null) {
            return Result.error("记录不存在");
        }
        return Result.success(fitness);
    }

    @PostMapping("/AddFitness")
    public Result addFitness(@RequestBody Fitness fitness) {
        Integer userId = ConcurrentContext.get();
        if (userId == null) {
            return Result.error("未找到用户信息");
        }
        fitness.setUser_id(userId);
        fitnessService.add(fitness);
        return Result.success();
    }

    @PostMapping("/SetFitness/{id}")
    public Result updateFitness(@PathVariable int id, @RequestBody Fitness fitness) {
        Fitness existing = fitnessService.getById(id);
        if (existing == null) {
            return Result.error("记录不存在");
        }
        fitness.setId(id);
        fitness.setUser_id(existing.getUser_id());
        fitnessService.update(fitness);
        return Result.success();
    }

    @DeleteMapping("/DeleteFitness/{id}")
    public Result deleteFitness(@PathVariable int id) {
        fitnessService.delete(id);
        return Result.success();
    }
}
