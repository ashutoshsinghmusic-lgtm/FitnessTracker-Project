package com.project.fitness_project.dto;

import com.project.fitness_project.model.ActivityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ActivityRequest {

    @NotBlank
    private ActivityType type;

    @Positive
    private Integer duration;

    @PositiveOrZero
    private Integer caloriesBurned;

    @NotNull
    private LocalDateTime startTime;

    private Map<String , Object> additionalMetrics;

}
