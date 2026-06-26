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

    @NotBlank(message = "Please enter a activity id")
    private String activityId;

    private List<String> improvements;
    @NotNull(message = "Enter Suggestion")
    private List<String> suggestions;
    private List<String> safety;

}
