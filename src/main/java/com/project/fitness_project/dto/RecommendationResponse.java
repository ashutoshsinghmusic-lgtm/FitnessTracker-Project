package com.project.fitness_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendationResponse {
    private String userId;
    private String activityId;

    private List<String> improvements;
    private List<String> suggestions;
    private List<String> safety;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
