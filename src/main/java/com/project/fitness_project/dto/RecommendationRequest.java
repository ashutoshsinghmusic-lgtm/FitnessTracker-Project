package com.project.fitness_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationRequest {

    @NotBlank
    @NotNull
    private String activityId;

    private List<String> improvements;
    @NotBlank
    private List<String> suggestions;
    private List<String> safety;

}
