package com.learning.healthpro.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeChunk {
    int id;
    String source;      // 来源：如"健康常识"、"用户数据"、"上传文档:xxx.txt"
    String category;    // 分类：body/diet/sleep/fitness/general
    String content;     // 文本内容（切片）
    String vector;      // 向量，JSON数组字符串存储
    String date;        // 创建日期
    int user_id;        // 所属用户ID，0表示系统公共知识
}
