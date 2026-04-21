package com.learning.healthpro.service.impl;

import com.learning.healthpro.entity.*;
import com.learning.healthpro.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HealthDataServiceImpl implements HealthDataService {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private BodyService bodyService;

    @Autowired
    private DietService dietService;

    @Autowired
    private SleepService sleepService;

    @Autowired
    private FitnessService fitnessService;

    @Override
    public Map<String, Object> getHealthSummary(int userId) {
        Map<String, Object> summary = new LinkedHashMap<>();

        // 用户基本信息
        User user = userInfoService.getInfo(userId);
        if (user != null) {
            Map<String, Object> userInfo = new LinkedHashMap<>();
            userInfo.put("年龄", user.getAge());
            userInfo.put("性别", user.getGender());
            summary.put("用户信息", userInfo);
        }

        // 身体数据 - 取最近5条
        ArrayList<Body> bodyList = bodyService.getBody(userId);
        if (bodyList != null && !bodyList.isEmpty()) {
            List<Map<String, Object>> bodyData = new ArrayList<>();
            int start = Math.max(0, bodyList.size() - 5);
            for (int i = start; i < bodyList.size(); i++) {
                Body b = bodyList.get(i);
                Map<String, Object> item = new LinkedHashMap<>();
                item.put("日期", b.getDate());
                item.put("身高cm", b.getHeight());
                item.put("体重kg", b.getWeight());
                item.put("体脂率%", b.getFat_pre());
                item.put("血糖", b.getSugar());
                item.put("收缩压", b.getPressure_h());
                item.put("舒张压", b.getPressure_l());
                if (b.getHeight() > 0) {
                    double bmi = b.getWeight() / ((b.getHeight() / 100) * (b.getHeight() / 100));
                    item.put("BMI", Math.round(bmi * 10) / 10.0);
                }
                bodyData.add(item);
            }
            summary.put("身体数据(最近" + bodyData.size() + "条)", bodyData);
        }

        // 饮食记录 - 取最近5条
        ArrayList<Diet> dietList = dietService.getDiet(userId);
        if (dietList != null && !dietList.isEmpty()) {
            List<Map<String, Object>> dietData = new ArrayList<>();
            int start = Math.max(0, dietList.size() - 5);
            for (int i = start; i < dietList.size(); i++) {
                Diet d = dietList.get(i);
                Map<String, Object> item = new LinkedHashMap<>();
                item.put("日期", d.getDate());
                item.put("早餐", d.getBreakfast());
                item.put("午餐", d.getLunch());
                item.put("晚餐", d.getDinner());
                item.put("备注", d.getRemark());
                dietData.add(item);
            }
            summary.put("饮食记录(最近" + dietData.size() + "条)", dietData);
        }

        // 睡眠记录 - 取最近5条
        ArrayList<Sleep> sleepList = sleepService.getAllByUserId(userId);
        if (sleepList != null && !sleepList.isEmpty()) {
            List<Map<String, Object>> sleepData = new ArrayList<>();
            int start = Math.max(0, sleepList.size() - 5);
            for (int i = start; i < sleepList.size(); i++) {
                Sleep s = sleepList.get(i);
                Map<String, Object> item = new LinkedHashMap<>();
                item.put("日期", s.getDate());
                item.put("总时长h", s.getDuration());
                item.put("深度睡眠h", s.getDeep_sleep());
                item.put("浅度睡眠h", s.getLight_sleep());
                item.put("REM睡眠h", s.getRem_sleep());
                sleepData.add(item);
            }
            summary.put("睡眠记录(最近" + sleepData.size() + "条)", sleepData);
        }

        // 健身记录 - 取最近5条
        ArrayList<Fitness> fitnessList = fitnessService.getAllByUserId(userId);
        if (fitnessList != null && !fitnessList.isEmpty()) {
            List<Map<String, Object>> fitnessData = new ArrayList<>();
            int start = Math.max(0, fitnessList.size() - 5);
            for (int i = start; i < fitnessList.size(); i++) {
                Fitness f = fitnessList.get(i);
                Map<String, Object> item = new LinkedHashMap<>();
                item.put("日期", f.getDate());
                item.put("运动类型", f.getType());
                item.put("时长min", f.getDuration());
                item.put("心率", f.getHeart_rate());
                item.put("消耗卡路里", f.getCalories());
                fitnessData.add(item);
            }
            summary.put("健身记录(最近" + fitnessData.size() + "条)", fitnessData);
        }

        return summary;
    }
}
