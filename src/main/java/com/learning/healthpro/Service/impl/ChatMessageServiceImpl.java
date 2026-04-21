package com.learning.healthpro.service.impl;

import com.learning.healthpro.entity.ChatMessage;
import com.learning.healthpro.mapper.ChatMessageMapper;
import com.learning.healthpro.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Override
    public ArrayList<ChatMessage> getByUserId(int userId) {
        return chatMessageMapper.getByUserId(userId);
    }

    @Override
    public void add(ChatMessage message) {
        chatMessageMapper.add(message);
    }

    @Override
    public void deleteByUserId(int userId) {
        chatMessageMapper.deleteByUserId(userId);
    }
}
