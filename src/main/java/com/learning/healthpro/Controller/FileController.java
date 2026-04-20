package com.learning.healthpro.controller;

import com.learning.healthpro.common.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/File")
public class FileController {

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    @PostMapping("/Upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("文件不能为空");
        }

        try {
            // 确保上传目录存在（使用绝对路径）
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath();
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String newFilename = UUID.randomUUID().toString() + extension;

            // 保存文件
            Path filePath = uploadPath.resolve(newFilename);
            file.transferTo(filePath.toFile());

            // 返回文件访问URL
            String url = "/File/Get/" + newFilename;
            return Result.success(url);

        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("文件上传失败: " + e.getMessage());
        }
    }

    @GetMapping("/Get/{filename}")
    public void getFile(@PathVariable String filename, HttpServletResponse response) {
        try {
            Path filePath = Paths.get(uploadDir).toAbsolutePath().resolve(filename);
            if (Files.exists(filePath)) {
                // 根据扩展名设置Content-Type
                String name = filePath.getFileName().toString().toLowerCase();
                if (name.endsWith(".jpg") || name.endsWith(".jpeg")) {
                    response.setContentType("image/jpeg");
                } else if (name.endsWith(".png")) {
                    response.setContentType("image/png");
                } else if (name.endsWith(".gif")) {
                    response.setContentType("image/gif");
                } else if (name.endsWith(".webp")) {
                    response.setContentType("image/webp");
                } else {
                    response.setContentType("application/octet-stream");
                }
                response.setContentLengthLong(Files.size(filePath));
                try (OutputStream os = response.getOutputStream()) {
                    Files.copy(filePath, os);
                    os.flush();
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (IOException e) {
            try {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (Exception ignored) {}
        }
    }
}
