package com.project.fitness_project.controller;

import com.project.fitness_project.dto.ActivityRecommendationResponse;
import com.project.fitness_project.dto.RecommendationRequest;
import com.project.fitness_project.dto.RecommendationResponse;
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

    @PostMapping("/generate")
    public ResponseEntity<RecommendationResponse> generateRecommendation(@RequestBody RecommendationRequest request){
        RecommendationResponse recommendationResponse = recommendationService.generateRecommendation(request);
        return ResponseEntity.ok(recommendationResponse);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserRecommendationResponse>> getUserRecommendations(@PathVariable String userId){
        List<UserRecommendationResponse> userRecommendationResponseList =
                                                recommendationService.getUserRecommendations(userId);

        return ResponseEntity.ok(userRecommendationResponseList);
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<ActivityRecommendationResponse>> getActivityRecommendations(@PathVariable String activityId){
        List<ActivityRecommendationResponse> activityRecommendationsResponseList =
                recommendationService.getActivityRecommendations(activityId);

        return ResponseEntity.ok(activityRecommendationsResponseList);
    }
}
