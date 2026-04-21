package com.learning.healthpro.controller;

import com.learning.healthpro.common.Result;
import com.learning.healthpro.context.ConcurrentContext;
import com.learning.healthpro.entity.ChatMessage;
import com.learning.healthpro.service.AiChatService;
import com.learning.healthpro.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/Ai")
public class AiChatController {

    @Autowired
    private AiChatService aiChatService;

    @Autowired
    private ChatMessageService chatMessageService;

    /**
     * 发送消息给AI
     */
    @PostMapping("/Chat")
    public Result chat(@RequestBody Map<String, String> params) {
        String message = params.get("message");
        if (message == null || message.trim().isEmpty()) {
            return Result.error("消息不能为空");
        }
        int userId = ConcurrentContext.get();
        String reply = aiChatService.chat(userId, message.trim());
        return Result.success(reply);
    }

    /**
     * 获取历史对话记录
     */
    @GetMapping("/History")
    public Result getHistory() {
        int userId = ConcurrentContext.get();
        ArrayList<ChatMessage> messages = chatMessageService.getByUserId(userId);
        return Result.success(messages);
    }

    /**
     * 清空对话记录
     */
    @DeleteMapping("/Clear")
    public Result clearHistory() {
        int userId = ConcurrentContext.get();
        chatMessageService.deleteByUserId(userId);
        return Result.success();
    }
}
