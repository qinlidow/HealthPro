package com.learning.healthpro.service;

import java.util.List;

public interface EmbeddingService {
    /**
     * 获取单个文本的向量
     */
    List<Double> embed(String text);

    /**
     * 批量获取文本向量
     */
    List<List<Double>> embedBatch(List<String> texts);
}
