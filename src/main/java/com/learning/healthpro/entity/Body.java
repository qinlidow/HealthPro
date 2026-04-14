package com.learning.healthpro.entity;

public class Body {

    int id;

    float height;

    float weight;

    float fat_pre;

    float sugar;

    float pressure_h;

    float pressure_l;

    String date;

    int user_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getFat_pre() {
        return fat_pre;
    }

    public void setFat_pre(float fat_pre) {
        this.fat_pre = fat_pre;
    }

    public float getSugar() {
        return sugar;
    }

    public void setSugar(float sugar) {
        this.sugar = sugar;
    }

    public float getPressure_h() {
        return pressure_h;
    }

    public void setPressure_h(float pressure_h) {
        this.pressure_h = pressure_h;
    }

    public float getPressure_l() {
        return pressure_l;
    }

    public void setPressure_l(float pressure_l) {
        this.pressure_l = pressure_l;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
