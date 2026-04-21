package com.learning.healthpro.service.impl;

import com.google.gson.Gson;
import com.learning.healthpro.entity.KnowledgeChunk;
import com.learning.healthpro.mapper.KnowledgeChunkMapper;
import com.learning.healthpro.service.EmbeddingService;
import com.learning.healthpro.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {

    private static final Logger log = LoggerFactory.getLogger(KnowledgeServiceImpl.class);

    @Autowired
    private KnowledgeChunkMapper knowledgeChunkMapper;

    @Autowired
    private EmbeddingService embeddingService;

    private final Gson gson = new Gson();

    private static final int CHUNK_SIZE = 300;
    private static final int CHUNK_OVERLAP = 50;

    @Override
    public void addText(int userId, String source, String category, String text) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        List<String> chunks = splitText(text, CHUNK_SIZE, CHUNK_OVERLAP);
        List<List<Double>> vectors = embeddingService.embedBatch(chunks);

        log.info("知识切片: 来源={}, 切片数={}, 向量数={}", source, chunks.size(), vectors.size());

        for (int i = 0; i < chunks.size(); i++) {
            String vectorJson = (i < vectors.size() && !vectors.get(i).isEmpty())
                    ? gson.toJson(vectors.get(i)) : null;

            if (vectorJson == null) {
                log.warn("切片[{}]向量化失败, content长度={}", i, chunks.get(i).length());
            }

            KnowledgeChunk chunk = KnowledgeChunk.builder()
                    .source(source)
                    .category(category)
                    .content(chunks.get(i))
                    .vector(vectorJson)
                    .date(today)
                    .user_id(userId)
                    .build();
            knowledgeChunkMapper.add(chunk);
        }
    }

    @Override
    public void deleteBySource(int userId, String source) {
        knowledgeChunkMapper.deleteBySourceAndUserId(source, userId);
    }

    @Override
    public void deleteByUserId(int userId) {
        knowledgeChunkMapper.deleteByUserId(userId);
    }

    @Override
    public ArrayList<KnowledgeChunk> getOwnByUserId(int userId) {
        return knowledgeChunkMapper.getOwnByUserId(userId);
    }

    @Override
    public int countByUserId(int userId) {
        return knowledgeChunkMapper.countByUserId(userId);
    }

    @Override
    public int countSystem() {
        return knowledgeChunkMapper.countSystem();
    }

    @Override
    public void rebuildIndex(int userId) {
        ArrayList<KnowledgeChunk> allChunks = knowledgeChunkMapper.getByUserId(userId);
        if (allChunks.isEmpty()) return;

        int batchSize = 10;
        for (int i = 0; i < allChunks.size(); i += batchSize) {
            int end = Math.min(i + batchSize, allChunks.size());
            List<String> texts = new ArrayList<>();
            List<KnowledgeChunk> batch = new ArrayList<>();

            for (int j = i; j < end; j++) {
                KnowledgeChunk chunk = allChunks.get(j);
                texts.add(chunk.getContent());
                batch.add(chunk);
            }

            List<List<Double>> vectors = embeddingService.embedBatch(texts);

            for (int j = 0; j < batch.size(); j++) {
                KnowledgeChunk chunk = batch.get(j);
                if (j < vectors.size() && !vectors.get(j).isEmpty()) {
                    knowledgeChunkMapper.deleteById(chunk.getId());
                    chunk.setId(0);
                    chunk.setVector(gson.toJson(vectors.get(j)));
                    knowledgeChunkMapper.add(chunk);
                }
            }
        }
    }

    private List<String> splitText(String text, int chunkSize, int overlap) {
        List<String> chunks = new ArrayList<>();
        String[] paragraphs = text.split("\n+");

        StringBuilder currentChunk = new StringBuilder();
        for (String paragraph : paragraphs) {
            paragraph = paragraph.trim();
            if (paragraph.isEmpty()) continue;

            if (paragraph.length() > chunkSize) {
                if (currentChunk.length() > 0) {
                    chunks.add(currentChunk.toString().trim());
                    currentChunk = new StringBuilder();
                }
                for (int i = 0; i < paragraph.length(); i += chunkSize - overlap) {
                    int end = Math.min(i + chunkSize, paragraph.length());
                    chunks.add(paragraph.substring(i, end).trim());
                }
            } else if (currentChunk.length() + paragraph.length() + 1 > chunkSize) {
                chunks.add(currentChunk.toString().trim());
                currentChunk = new StringBuilder(paragraph);
            } else {
                if (currentChunk.length() > 0) {
                    currentChunk.append("\n");
                }
                currentChunk.append(paragraph);
            }
        }

        if (currentChunk.length() > 0) {
            chunks.add(currentChunk.toString().trim());
        }

        return chunks;
    }
}
