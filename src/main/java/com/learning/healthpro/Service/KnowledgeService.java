package com.learning.healthpro.service;

import com.learning.healthpro.entity.KnowledgeChunk;

import java.util.ArrayList;

public interface KnowledgeService {
    /**
     * 添加文本到知识库（自动切片+向量化+存储）
     */
    void addText(int userId, String source, String category, String text);

    /**
     * 删除指定来源的知识（仅限当前用户的）
     */
    void deleteBySource(int userId, String source);

    /**
     * 清空当前用户的所有知识
     */
    void deleteByUserId(int userId);

    /**
     * 获取当前用户自己的知识切片（不含系统公共知识）
     */
    ArrayList<KnowledgeChunk> getOwnByUserId(int userId);

    /**
     * 获取当前用户的知识切片数量
     */
    int countByUserId(int userId);

    /**
     * 获取系统公共知识数量
     */
    int countSystem();

    /**
     * 重建当前用户切片的向量索引
     */
    void rebuildIndex(int userId);
}
