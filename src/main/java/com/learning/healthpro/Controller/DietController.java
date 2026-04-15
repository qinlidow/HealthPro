package com.learning.healthpro.controller;

import com.learning.healthpro.common.Result;
import com.learning.healthpro.context.ConcurrentContext;
import com.learning.healthpro.entity.Diet;
import com.learning.healthpro.service.DietService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequestMapping("/Diet")
@RestController
public class DietController {
    private final DietService dietService;
    public DietController(DietService dietService){
        this.dietService = dietService;
    }

    @GetMapping("/GetDiet")
    public Result getDiet(){
        Integer userid = ConcurrentContext.get();
        if(userid == null){
            return Result.error("未找到用户信息");
        }
        return Result.success(dietService.getDiet(userid));
    }

    @PostMapping("/AddDiet")
    public Result addDiet(@RequestBody Diet diet){
        Integer userid = ConcurrentContext.get();
        if(userid == null){
            return Result.error("未找到用户信息");
        }
        dietService.addDiet(diet,userid);
        return Result.success();
    }

    @PostMapping("/SetDiet/{id}")
    public Result setDiet(@RequestBody Diet diet,@PathVariable int id){
        Integer userid = ConcurrentContext.get();
        if(userid == null){
            return Result.error("未找到用户信息");
        }
        dietService.setDiet(diet,id);
        return Result.success();
    }

    @DeleteMapping("/DeleteDiet/{id}")
    public Result deleteDiet(@PathVariable int id){
        Integer userid = ConcurrentContext.get();
        if(userid == null){
            return Result.error("未找到用户信息");
        }
        dietService.deleteDiet(id);
        return Result.success();
    }
}
