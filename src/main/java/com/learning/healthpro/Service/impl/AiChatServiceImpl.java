package com.learning.healthpro.service.impl;

import com.google.gson.*;
import com.learning.healthpro.config.AiConfig;
import com.learning.healthpro.entity.ChatMessage;
import com.learning.healthpro.entity.KnowledgeChunk;
import com.learning.healthpro.service.AiChatService;
import com.learning.healthpro.service.ChatMessageService;
import com.learning.healthpro.service.HealthDataService;
import com.learning.healthpro.service.VectorSearchService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class AiChatServiceImpl implements AiChatService {

    @Autowired
    private AiConfig aiConfig;

    @Autowired
    private HealthDataService healthDataService;

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private VectorSearchService vectorSearchService;

    private static final MediaType JSON_TYPE = MediaType.get("application/json; charset=utf-8");
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static final String SYSTEM_PROMPT = """
            你是HealthPro健康管理系统的AI助手，专注于为用户提供专业的健康咨询和个性化建议。

            你的职责：
            1. 根据用户的健康数据（身体指标、饮食、睡眠、运动等）给出个性化建议
            2. 回答用户关于健康、营养、运动、睡眠等方面的问题
            3. 分析用户的健康趋势，提醒潜在风险
            4. 给出饮食推荐、运动计划等实用建议

            回答要求：
            - 回答要专业、准确、有温度
            - 优先基于检索到的知识库内容回答，确保建议有科学依据
            - 结合用户的实际数据给出个性化建议，不要泛泛而谈
            - 如果用户数据不足，先给出通用建议，并提醒用户多记录数据以获得更精准的建议
            - 涉及医疗诊断时，提醒用户仅供参考，建议就医
            - 回答简洁明了，重点突出，使用分点或分段让内容更易读
            """;

    @Override
    public String chat(int userId, String userMessage) {
        // 保存用户消息
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        chatMessageService.add(ChatMessage.builder()
                .role("user").content(userMessage).date(today).user_id(userId).build());

        // ===== RAG: 检索相关知识（系统公共 + 用户私有） =====
        List<KnowledgeChunk> relevantChunks = vectorSearchService.search(userId, userMessage, 3);
        String ragContext = buildRagContext(relevantChunks);

        // 获取用户健康数据
        Map<String, Object> healthSummary = healthDataService.getHealthSummary(userId);
        String healthDataStr = gson.toJson(healthSummary);

        // 构建系统提示词：RAG知识 + 用户健康数据
        StringBuilder systemContent = new StringBuilder(SYSTEM_PROMPT);

        // 注入RAG检索到的知识
        if (!ragContext.isEmpty()) {
            systemContent.append("\n\n【检索到的健康知识库内容】\n").append(ragContext);
        }

        // 注入用户健康数据
        systemContent.append("\n\n【当前用户的健康数据】\n").append(healthDataStr);

        // 获取历史对话（最近20条）
        ArrayList<ChatMessage> history = chatMessageService.getByUserId(userId);
        int start = Math.max(0, history.size() - 20);

        // 构建消息列表
        JsonArray messages = new JsonArray();

        // 系统消息
        JsonObject sysMsg = new JsonObject();
        sysMsg.addProperty("role", "system");
        sysMsg.addProperty("content", systemContent.toString());
        messages.add(sysMsg);

        // 历史消息
        for (int i = start; i < history.size(); i++) {
            ChatMessage msg = history.get(i);
            JsonObject msgObj = new JsonObject();
            msgObj.addProperty("role", msg.getRole());
            msgObj.addProperty("content", msg.getContent());
            messages.add(msgObj);
        }

        // 调用大模型API
        String aiReply = callAiApi(messages);

        // 保存AI回复
        chatMessageService.add(ChatMessage.builder()
                .role("assistant").content(aiReply).date(today).user_id(userId).build());

        return aiReply;
    }

    /**
     * 构建RAG上下文：将检索到的知识切片拼接为文本
     */
    private String buildRagContext(List<KnowledgeChunk> chunks) {
        if (chunks == null || chunks.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chunks.size(); i++) {
            KnowledgeChunk chunk = chunks.get(i);
            sb.append("[").append(i + 1).append("] 来源: ").append(chunk.getSource());
            sb.append(" | 分类: ").append(chunk.getCategory()).append("\n");
            sb.append(chunk.getContent()).append("\n\n");
        }
        return sb.toString();
    }

    private String callAiApi(JsonArray messages) {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", aiConfig.getModel());
        requestBody.add("messages", messages);
        requestBody.addProperty("max_tokens", aiConfig.getMaxTokens());
        requestBody.addProperty("temperature", aiConfig.getTemperature());

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(aiConfig.getApiUrl())
                .addHeader("Authorization", "Bearer " + aiConfig.getApiKey())
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(requestBody.toString(), JSON_TYPE))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return "AI服务暂时不可用，请稍后再试。（错误码：" + response.code() + "）";
            }

            String responseBody = response.body().string();
            JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

            JsonArray choices = jsonResponse.getAsJsonArray("choices");
            if (choices != null && !choices.isEmpty()) {
                JsonObject firstChoice = choices.get(0).getAsJsonObject();
                JsonObject message = firstChoice.getAsJsonObject("message");
                if (message != null && message.has("content")) {
                    return message.get("content").getAsString();
                }
            }
            return "AI回复解析失败，请重试。";
        } catch (IOException e) {
            return "网络连接失败，请检查网络后重试。";
        }
    }
}
