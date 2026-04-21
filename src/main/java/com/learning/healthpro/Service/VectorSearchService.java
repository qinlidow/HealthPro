package com.learning.healthpro.service;

import com.learning.healthpro.entity.KnowledgeChunk;

import java.util.List;

public interface VectorSearchService {
    /**
     * 检索与查询最相关的Top-K知识切片
     * 搜索范围：系统公共知识(user_id=0) + 指定用户的私有知识
     */
    List<KnowledgeChunk> search(int userId, String query, int topK);
}
