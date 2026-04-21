package com.learning.healthpro.service;

import org.springframework.web.multipart.MultipartFile;

public interface DocumentParseService {
    /**
     * 解析上传的文件，提取纯文本内容
     * 支持：.docx, .txt
     */
    String parse(MultipartFile file) throws Exception;

    /**
     * 从文件名中提取不带扩展名的文件名
     */
    String getFileNameWithoutExtension(String filename);
}
