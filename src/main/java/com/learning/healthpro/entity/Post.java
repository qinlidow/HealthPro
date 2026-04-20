package com.learning.healthpro.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    int id;

    String category;

    String title;

    String content;

    String image;

    int likes;

    int comments;

    String date;

    int user_id;

    String user_name;

    String user_avatar;

    int status;

    int is_top;
}
