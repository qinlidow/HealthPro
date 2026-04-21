package com.learning.healthpro.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "ai")
public class AiConfig {
    private String apiKey;
    private String apiUrl;
    private String model;
    private int maxTokens;
    private double temperature;
    private String embeddingUrl;
    private String embeddingModel;
}
