package com.learning.healthpro.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Diet {

    int id;

    String breakfast;

    String lunch;

    String dinner;

    String remark;

    String date;

    int user_id;

}
