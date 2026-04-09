package com.learning.healthpro.common;

import lombok.Builder;
import lombok.Data;

@Data
public class Result {
    private int code;

    private String message;

    private Object data;

    public Result() {}

    public static Result success(){
        return success(null);
    }

    public static Result success(Object data){
        Result res = new Result();
        res.setCode(200);
        res.setMessage("操作成功");
        res.setData(data);
        return res;
    }

    public static Result error(){
        return error("操作失败");
    }

    public static Result error(String message){
        Result res = new Result();
        res.setCode(500);
        res.setMessage(message);
        res.setData(null);
        return res;
    }

    public static Result error(int code,String message){
        Result res = new Result();
        res.setCode(code);
        res.setMessage(message);
        res.setData(null);
        return res;
    }
}
