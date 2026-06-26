package com.project.fitness_project.service;

import com.project.fitness_project.dto.ActivityRequest;
import com.project.fitness_project.dto.ActivityResponse;
import com.project.fitness_project.model.Activity;
import com.project.fitness_project.model.User;
import com.project.fitness_project.repository.ActivityRepository;
import com.project.fitness_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    public final ActivityRepository activityRepository;
    public final UserRepository userRepository;

    public ActivityResponse saveActivities(ActivityRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Invalid User Id : " + request.getUserId()));

        Activity activity = Activity.builder()
                .type(request.getType())
                .caloriesBurned(request.getCaloriesBurned())
                .additionalMetrics(request.getAdditionalMetrics())
                .duration(request.getDuration())
                .startTime(request.getStartTime())
                .user(user)
                .build();


        Activity savedActivity = activityRepository.save(activity);


        return mapToResponse(savedActivity);
    }

    private ActivityResponse mapToResponse(Activity activity) {
        ActivityResponse response =
                ActivityResponse.builder()
                        .id(activity.getId())
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

    public List<ActivityResponse> getAllActivitiesById(String userId) {
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
