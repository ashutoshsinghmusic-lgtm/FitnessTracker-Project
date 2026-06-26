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
public class UserRecommendationResponse {
    private String userId;

    private List<String> improvements;
    private List<String> suggestions;
    private List<String> safety;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
