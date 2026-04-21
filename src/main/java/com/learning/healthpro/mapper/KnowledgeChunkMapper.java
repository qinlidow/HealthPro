package com.learning.healthpro.mapper;

import com.learning.healthpro.entity.KnowledgeChunk;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface KnowledgeChunkMapper {
    /**
     * 获取指定用户的知识切片（包含系统公共知识 user_id=0）
     */
    ArrayList<KnowledgeChunk> getByUserId(@Param("userId") int userId);

    /**
     * 获取仅属于指定用户的知识切片（不含系统公共知识）
     */
    ArrayList<KnowledgeChunk> getOwnByUserId(@Param("userId") int userId);

    /**
     * 获取系统公共知识（user_id=0）
     */
    ArrayList<KnowledgeChunk> getSystemKnowledge();

    void add(KnowledgeChunk chunk);

    void deleteById(int id);

    void deleteBySourceAndUserId(@Param("source") String source, @Param("userId") int userId);

    void deleteByUserId(@Param("userId") int userId);

    int countByUserId(@Param("userId") int userId);

    int countSystem();
}
