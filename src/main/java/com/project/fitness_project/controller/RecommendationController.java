package com.project.fitness_project.controller;

import com.project.fitness_project.dto.ActivityRecommendationResponse;
import com.project.fitness_project.dto.UserRecommendationResponse;
import com.project.fitness_project.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

    public final RecommendationService recommendationService;

    @GetMapping("/user")
    public ResponseEntity<List<UserRecommendationResponse>> getUserRecommendations(){
        List<UserRecommendationResponse> userRecommendationResponseList =
                                                recommendationService.getUserRecommendations();

        return ResponseEntity.ok(userRecommendationResponseList);
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<ActivityRecommendationResponse>> getActivityRecommendations(@PathVariable String activityId) {
        List<ActivityRecommendationResponse> activityRecommendationsResponseList =
                recommendationService.getActivityRecommendations(activityId);

        return ResponseEntity.ok(activityRecommendationsResponseList);
    }
}
