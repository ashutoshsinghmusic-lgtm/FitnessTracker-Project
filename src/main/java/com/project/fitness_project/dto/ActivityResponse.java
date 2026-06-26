package com.project.fitness_project.dto;

import com.project.fitness_project.model.ActivityType;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.BindParam;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Data
public class ActivityResponse {

    private String activityId;
    private String userId;
    private ActivityType type;
    private Map<String , Object> additionalMetrics;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
