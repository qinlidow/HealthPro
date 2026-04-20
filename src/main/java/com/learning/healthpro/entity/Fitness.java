package com.learning.healthpro.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fitness {

    int id;

    String type;

    float duration;

    float heart_rate;

    float calories;

    String date;

    int user_id;
}
