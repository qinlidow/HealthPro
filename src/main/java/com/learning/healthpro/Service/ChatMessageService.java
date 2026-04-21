package com.learning.healthpro.service;

import com.learning.healthpro.entity.ChatMessage;

import java.util.ArrayList;

public interface ChatMessageService {
    ArrayList<ChatMessage> getByUserId(int userId);

    void add(ChatMessage message);

    void deleteByUserId(int userId);
}
