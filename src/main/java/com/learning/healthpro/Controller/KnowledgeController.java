package com.learning.healthpro.controller;

import com.learning.healthpro.common.Result;
import com.learning.healthpro.context.ConcurrentContext;
import com.learning.healthpro.entity.KnowledgeChunk;
import com.learning.healthpro.service.DocumentParseService;
import com.learning.healthpro.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/Knowledge")
public class KnowledgeController {

    @Autowired
    private KnowledgeService knowledgeService;

    @Autowired
    private DocumentParseService documentParseService;

    /**
     * 上传文档到知识库（支持 .docx, .txt）
     */
    @PostMapping("/Upload")
    public Result upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("category") String category) {
        if (file.isEmpty()) {
            return Result.error("文件不能为空");
        }
        if (category == null || category.trim().isEmpty()) {
            return Result.error("分类不能为空");
        }

        int userId = ConcurrentContext.get();

        try {
            String text = documentParseService.parse(file);
            if (text.isEmpty()) {
                return Result.error("文档内容为空，请检查文件");
            }

            String source = documentParseService.getFileNameWithoutExtension(file.getOriginalFilename());
            knowledgeService.addText(userId, source, category.trim(), text);

            return Result.success("文档「" + source + "」上传成功，已自动切片和向量化");
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("文档解析失败：" + e.getMessage());
        }
    }

    /**
     * 手动添加文本到知识库
     */
    @PostMapping("/Add")
    public Result add(@RequestBody Map<String, String> params) {
        String source = params.get("source");
        String category = params.get("category");
        String text = params.get("text");

        if (source == null || source.trim().isEmpty()) {
            return Result.error("来源不能为空");
        }
        if (category == null || category.trim().isEmpty()) {
            return Result.error("分类不能为空");
        }
        if (text == null || text.trim().isEmpty()) {
            return Result.error("文本内容不能为空");
        }

        int userId = ConcurrentContext.get();
        knowledgeService.addText(userId, source.trim(), category.trim(), text.trim());
        return Result.success("知识添加成功");
    }

    /**
     * 获取当前用户的知识切片列表
     */
    @GetMapping("/List")
    public Result list() {
        int userId = ConcurrentContext.get();
        ArrayList<KnowledgeChunk> chunks = knowledgeService.getOwnByUserId(userId);
        return Result.success(chunks);
    }

    /**
     * 获取当前用户的知识数量 + 系统公共知识数量
     */
    @GetMapping("/Count")
    public Result count() {
        int userId = ConcurrentContext.get();
        int userCount = knowledgeService.countByUserId(userId);
        int systemCount = knowledgeService.countSystem();
        Map<String, Integer> counts = Map.of(
                "user", userCount,
                "system", systemCount,
                "total", userCount + systemCount
        );
        return Result.success(counts);
    }

    /**
     * 删除指定来源的知识（仅限当前用户的）
     */
    @DeleteMapping("/Delete")
    public Result delete(@RequestParam String source) {
        int userId = ConcurrentContext.get();
        knowledgeService.deleteBySource(userId, source);
        return Result.success("删除成功");
    }

    /**
     * 清空当前用户的所有知识
     */
    @DeleteMapping("/Clear")
    public Result clear() {
        int userId = ConcurrentContext.get();
        knowledgeService.deleteByUserId(userId);
        return Result.success("您的知识库已清空");
    }

    /**
     * 重建当前用户的向量索引
     */
    @PostMapping("/RebuildIndex")
    public Result rebuildIndex() {
        int userId = ConcurrentContext.get();
        knowledgeService.rebuildIndex(userId);
        return Result.success("索引重建完成");
    }
}
