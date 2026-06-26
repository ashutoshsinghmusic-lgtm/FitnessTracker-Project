package com.project.fitness_project.controller;

import com.project.fitness_project.dto.ActivityRecommendationResponse;
import com.project.fitness_project.dto.RecommendationRequest;
import com.project.fitness_project.dto.RecommendationResponse;
import com.project.fitness_project.dto.UserRecommendationResponse;
import com.project.fitness_project.service.RecommendationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

    public final RecommendationService recommendationService;

    @PostMapping("/generate")
    public ResponseEntity<RecommendationResponse> generateRecommendation(@Valid @RequestBody RecommendationRequest request) throws AccessDeniedException {
        RecommendationResponse recommendationResponse = recommendationService.generateRecommendation(request);
        return ResponseEntity.ok(recommendationResponse);
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserRecommendationResponse>> getUserRecommendations(){
        List<UserRecommendationResponse> userRecommendationResponseList =
                                                recommendationService.getUserRecommendations();

        return ResponseEntity.ok(userRecommendationResponseList);
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<ActivityRecommendationResponse>> getActivityRecommendations(@PathVariable String activityId) throws AccessDeniedException {
        List<ActivityRecommendationResponse> activityRecommendationsResponseList =
                recommendationService.getActivityRecommendations(activityId);

        return ResponseEntity.ok(activityRecommendationsResponseList);
    }
}
