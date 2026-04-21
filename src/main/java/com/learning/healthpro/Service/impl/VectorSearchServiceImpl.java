package com.learning.healthpro.service.impl;

import com.google.gson.Gson;
import com.learning.healthpro.entity.KnowledgeChunk;
import com.learning.healthpro.mapper.KnowledgeChunkMapper;
import com.learning.healthpro.service.EmbeddingService;
import com.learning.healthpro.service.VectorSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VectorSearchServiceImpl implements VectorSearchService {

    @Autowired
    private EmbeddingService embeddingService;

    @Autowired
    private KnowledgeChunkMapper knowledgeChunkMapper;

    private final Gson gson = new Gson();

    @Override
    public List<KnowledgeChunk> search(int userId, String query, int topK) {
        // 1. 获取查询的向量
        List<Double> queryVector = embeddingService.embed(query);
        if (queryVector.isEmpty()) {
            return Collections.emptyList();
        }

        // 2. 获取知识切片：系统公共知识(user_id=0) + 当前用户的私有知识
        ArrayList<KnowledgeChunk> allChunks = knowledgeChunkMapper.getByUserId(userId);

        if (allChunks.isEmpty()) {
            return Collections.emptyList();
        }

        // 3. 计算余弦相似度并排序
        List<ScoredChunk> scored = new ArrayList<>();
        for (KnowledgeChunk chunk : allChunks) {
            if (chunk.getVector() == null || chunk.getVector().isEmpty()) {
                continue;
            }
            List<Double> chunkVector = gson.fromJson(chunk.getVector(), List.class);
            double similarity = cosineSimilarity(queryVector, chunkVector);
            scored.add(new ScoredChunk(chunk, similarity));
        }

        // 4. 按相似度降序排列，取Top-K
        scored.sort((a, b) -> Double.compare(b.score, a.score));

        return scored.stream()
                .limit(topK)
                .map(s -> s.chunk)
                .collect(Collectors.toList());
    }

    private double cosineSimilarity(List<Double> a, List<Double> b) {
        if (a.size() != b.size() || a.isEmpty()) {
            return 0.0;
        }

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (int i = 0; i < a.size(); i++) {
            double va = a.get(i);
            double vb = b.get(i);
            dotProduct += va * vb;
            normA += va * va;
            normB += vb * vb;
        }

        if (normA == 0.0 || normB == 0.0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    private static class ScoredChunk {
        KnowledgeChunk chunk;
        double score;

        ScoredChunk(KnowledgeChunk chunk, double score) {
            this.chunk = chunk;
            this.score = score;
        }
    }
}
