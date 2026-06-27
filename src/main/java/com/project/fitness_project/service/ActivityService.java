package com.project.fitness_project.service;

import com.project.fitness_project.dto.ActivityRequest;
import com.project.fitness_project.dto.ActivityResponse;
import com.project.fitness_project.model.Activity;
import com.project.fitness_project.model.Recommendation;
import com.project.fitness_project.model.User;
import com.project.fitness_project.repository.ActivityRepository;
import com.project.fitness_project.repository.RecommendationRepository;
import com.project.fitness_project.repository.UserRepository;
import com.project.fitness_project.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    public final ActivityRepository activityRepository;
    public final UserRepository userRepository;
    public final SecurityUtils securityUtils;
    public final RecommendationRepository recommendationRepository;
    public final GeminiService geminiService;

    public ActivityResponse saveActivities(ActivityRequest request) {

        String userId = securityUtils.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid User Id : " + userId));

        Activity activity = Activity.builder()
                .type(request.getType())
                .caloriesBurned(request.getCaloriesBurned())
                .additionalMetrics(request.getAdditionalMetrics())
                .duration(request.getDuration())
                .startTime(request.getStartTime())
                .user(user)
                .build();


        Activity savedActivity = activityRepository.save(activity);


        try {
            Map<String, List<String>> geminiResult = geminiService.generateRecommendation(savedActivity);

            Recommendation recommendation = Recommendation.builder()
                    .improvements(geminiResult.get("improvements"))
                    .suggestions(geminiResult.get("suggestions"))
                    .safety(geminiResult.get("safety"))
                    .user(user)
                    .activity(savedActivity)
                    .build();

            recommendationRepository.save(recommendation);

        } catch (Exception e) {

            System.out.println("AI Recommendation generate nahi ho payi: " + e.getMessage());
        }


        return mapToResponse(savedActivity);
    }

    private ActivityResponse mapToResponse(Activity activity) {
        ActivityResponse response =
                ActivityResponse.builder()
                        .activityId(activity.getId())
                        .userId(activity.getUser().getId())
                        .type(activity.getType())
                        .additionalMetrics(activity.getAdditionalMetrics())
                        .duration(activity.getDuration())
                        .startTime(activity.getStartTime())
                        .createdAt(activity.getCreatedAt())
                        .updatedAt(activity.getUpdatedAt())
                        .caloriesBurned(activity.getCaloriesBurned())
                        .build();

        return response;

    }

    public List<ActivityResponse> getAllActivitiesById() {

        String userId = securityUtils.getCurrentUserId();


        List<Activity> allActivities  = activityRepository.findByUserId(userId);

//        List<ActivityResponse> activityResponseList = new ArrayList<>();
//        for(Activity a : allActivities){
//            activityResponseList.add(mapToResponse(a));
//        }

        return allActivities.stream()
                            .map(a -> mapToResponse(a))
                            .collect(Collectors.toList());

    }
}
