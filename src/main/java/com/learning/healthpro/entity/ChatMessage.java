package com.learning.healthpro.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    int id;
    String role;    // user 或 assistant
    String content;
    String date;
    int user_id;
}
