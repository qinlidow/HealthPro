package com.learning.healthpro.service.impl;

import com.google.gson.*;
import com.learning.healthpro.config.AiConfig;
import com.learning.healthpro.service.EmbeddingService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmbeddingServiceImpl implements EmbeddingService {

    private static final Logger log = LoggerFactory.getLogger(EmbeddingServiceImpl.class);

    @Autowired
    private AiConfig aiConfig;

    private static final MediaType JSON_TYPE = MediaType.get("application/json; charset=utf-8");

    @Override
    public List<Double> embed(String text) {
        List<List<Double>> results = embedBatch(Collections.singletonList(text));
        return results.isEmpty() ? Collections.emptyList() : results.get(0);
    }

    @Override
    public List<List<Double>> embedBatch(List<String> texts) {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", aiConfig.getEmbeddingModel());

        JsonArray inputArray = new JsonArray();
        for (String text : texts) {
            inputArray.add(text);
        }
        requestBody.add("input", inputArray);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(aiConfig.getEmbeddingUrl())
                .addHeader("Authorization", "Bearer " + aiConfig.getApiKey())
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(requestBody.toString(), JSON_TYPE))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = "";
                try { errorBody = response.body().string(); } catch (Exception ignored) {}
                log.error("Embedding API调用失败, 状态码: {}, 响应: {}", response.code(), errorBody);
                return Collections.emptyList();
            }

            String responseBody = response.body().string();
            JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonArray data = jsonResponse.getAsJsonArray("data");

            List<List<Double>> embeddings = new ArrayList<>();
            if (data != null) {
                // 按index排序确保顺序正确
                List<JsonObject> dataList = new ArrayList<>();
                for (JsonElement elem : data) {
                    dataList.add(elem.getAsJsonObject());
                }
                dataList.sort(Comparator.comparingInt(o -> o.get("index").getAsInt()));

                for (JsonObject obj : dataList) {
                    JsonArray embedding = obj.getAsJsonArray("embedding");
                    List<Double> vector = new ArrayList<>();
                    for (JsonElement e : embedding) {
                        vector.add(e.getAsDouble());
                    }
                    embeddings.add(vector);
                }
            }
            log.info("Embedding成功, 输入{}条, 输出{}条向量", texts.size(), embeddings.size());
            return embeddings;
        } catch (IOException e) {
            log.error("Embedding API网络异常: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}
