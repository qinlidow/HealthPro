package com.learning.healthpro.service;

import java.util.Map;

public interface HealthDataService {
    Map<String, Object> getHealthSummary(int userId);
}
