package com.learning.healthpro.controller;

import com.learning.healthpro.common.Result;
import com.learning.healthpro.entity.Body;
import com.learning.healthpro.service.BodyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/Body")
@RestController
public class BodyController {

    @Autowired
    private BodyService bodyService;

    @PostMapping("/GetBody")
    public Result getBody(HttpServletRequest request){
        Integer id = (Integer) request.getAttribute("userId");
        System.out.println(id);
        if (id == null){
            return Result.error("未找到用户信息");
        }
        return Result.success(bodyService.getBody(id));
    }

    @PostMapping("/AddBody")
    public Result addBody(@RequestBody Body body, HttpServletRequest request){
        Integer id = (Integer) request.getAttribute("userId");
        System.out.println(id);
        if(id == null){
            return Result.error("未找到用户信息");
        }
        bodyService.addBody(body,id);
        return Result.success();
    }

    @PostMapping("/SetBody/{id}")
    public  Result setBody(@RequestBody Body body, @PathVariable int id,HttpServletRequest request){
        Integer user_id = (Integer) request.getAttribute("userId");
        System.out.println(user_id);
        if(user_id == null){
            return Result.error("未找到用户信息");
        }
        bodyService.setBody(body,id);
        return Result.success();
    }

    @DeleteMapping("/DeleteBody/{id}")
    public Result deleteBody(@PathVariable int id,HttpServletRequest request){
       Integer user_id = (Integer) request.getAttribute("userId");
       if(user_id == null){
           return Result.error("未找到用户信息");
       }
       bodyService.deleteBody(id);
       return Result.success();
    }
}
