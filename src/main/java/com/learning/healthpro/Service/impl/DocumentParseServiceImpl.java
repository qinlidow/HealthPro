package com.learning.healthpro.service.impl;

import com.learning.healthpro.service.DocumentParseService;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class DocumentParseServiceImpl implements DocumentParseService {

    @Override
    public String parse(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename();
        if (filename == null) {
            throw new IllegalArgumentException("文件名不能为空");
        }

        String lowerName = filename.toLowerCase();

        if (lowerName.endsWith(".docx")) {
            return parseDocx(file);
        } else if (lowerName.endsWith(".txt")) {
            return parseTxt(file);
        } else {
            throw new IllegalArgumentException("不支持的文件格式，请上传 .docx 或 .txt 文件");
        }
    }

    /**
     * 解析 .docx 文件（Word 2007+）
     */
    private String parseDocx(MultipartFile file) throws IOException {
        StringBuilder text = new StringBuilder();

        try (InputStream is = file.getInputStream();
             XWPFDocument document = new XWPFDocument(is)) {

            // 解析段落
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                String line = paragraph.getText();
                if (line != null && !line.trim().isEmpty()) {
                    text.append(line.trim()).append("\n");
                }
            }

            // 解析表格
            for (XWPFTable table : document.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    StringBuilder rowText = new StringBuilder();
                    for (XWPFTableCell cell : row.getTableCells()) {
                        String cellText = cell.getText();
                        if (cellText != null && !cellText.trim().isEmpty()) {
                            if (rowText.length() > 0) {
                                rowText.append(" | ");
                            }
                            rowText.append(cellText.trim());
                        }
                    }
                    if (rowText.length() > 0) {
                        text.append(rowText).append("\n");
                    }
                }
                text.append("\n");
            }
        }

        return text.toString().trim();
    }

    /**
     * 解析 .txt 文件
     */
    private String parseTxt(MultipartFile file) throws IOException {
        return new String(file.getBytes(), StandardCharsets.UTF_8).trim();
    }

    @Override
    public String getFileNameWithoutExtension(String filename) {
        if (filename == null) return "未知文档";
        int dotIndex = filename.lastIndexOf('.');
        return dotIndex > 0 ? filename.substring(0, dotIndex) : filename;
    }
}
