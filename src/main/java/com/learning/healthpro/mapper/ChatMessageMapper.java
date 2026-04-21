package com.learning.healthpro.mapper;

import com.learning.healthpro.entity.ChatMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface ChatMessageMapper {
    ArrayList<ChatMessage> getByUserId(int userId);

    void add(ChatMessage message);

    void deleteByUserId(int userId);
}
