package com.project.fitness_project.service;

import com.project.fitness_project.dto.ActivityRecommendationResponse;
import com.project.fitness_project.dto.RecommendationRequest;
import com.project.fitness_project.dto.RecommendationResponse;
import com.project.fitness_project.dto.UserRecommendationResponse;
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
import org.springframework.security.access.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    public final RecommendationRepository recommendationRepository;
    public final UserRepository userRepository;
    public final ActivityRepository activityRepository;
    public final SecurityUtils securityUtils;


    public RecommendationResponse generateRecommendation(RecommendationRequest request) throws AccessDeniedException {

        String userId = securityUtils.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid UserId : " + userId));
        Activity activity = activityRepository.findById(request.getActivityId())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid ActivityId " + request.getActivityId()));

        securityUtils.isActivityBelongsToCurrentUser(activity,userId);


        Recommendation recommendation = Recommendation.builder()
                .improvements(request.getImprovements())
                .safety(request.getSafety())
                .suggestions(request.getSuggestions())
                .user(user)
                .activity(activity)
                .build();

        Recommendation savedRecommendation = recommendationRepository.save(recommendation);

        return mapToRecommendationResponse(savedRecommendation);
    }

    private RecommendationResponse mapToRecommendationResponse(Recommendation savedRecommendation) {

        RecommendationResponse response = RecommendationResponse.builder()
                .activityId(savedRecommendation.getActivity().getId())
                .improvements(savedRecommendation.getImprovements())
                .updatedAt(savedRecommendation.getUpdatedAt())
                .userId(savedRecommendation.getUser().getId())
                .suggestions(savedRecommendation.getSuggestions())
                .safety(savedRecommendation.getSafety())
                .createdAt(savedRecommendation.getCreatedAt())
                .activityId(savedRecommendation.getActivity().getId())
                .build();


        return response;
    }

    private UserRecommendationResponse mapToUserRecommendationResponse(Recommendation savedRecommendation) {

        UserRecommendationResponse response = UserRecommendationResponse.builder()
                .improvements(savedRecommendation.getImprovements())
                .updatedAt(savedRecommendation.getUpdatedAt())
                .userId(savedRecommendation.getUser().getId())
                .suggestions(savedRecommendation.getSuggestions())
                .safety(savedRecommendation.getSafety())
                .createdAt(savedRecommendation.getCreatedAt())
                .build();


        return response;
    }


    private ActivityRecommendationResponse mapToActivityRecommendationResponse(Recommendation savedRecommendation) {

        ActivityRecommendationResponse response = ActivityRecommendationResponse.builder()
                .activityId(savedRecommendation.getActivity().getId())
                .improvements(savedRecommendation.getImprovements())
                .updatedAt(savedRecommendation.getUpdatedAt())
                .suggestions(savedRecommendation.getSuggestions())
                .safety(savedRecommendation.getSafety())
                .createdAt(savedRecommendation.getCreatedAt())
                .build();


        return response;
    }

    public List<UserRecommendationResponse> getUserRecommendations() {

        String userId = securityUtils.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid UserId : " + userId));



        List<Recommendation> userRecomendations = recommendationRepository.findByUserId(userId);

        return userRecomendations.stream()
                .map(a-> mapToUserRecommendationResponse(a))
                .collect(Collectors.toList());
    }

    public List<ActivityRecommendationResponse> getActivityRecommendations(String activityId) throws AccessDeniedException {

        String userId = securityUtils.getCurrentUserId();

        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new UsernameNotFoundException("Invalide ActivityId : "+ activityId));



        securityUtils.isActivityBelongsToCurrentUser(activity,userId);

        List<Recommendation> activityRecommendations = recommendationRepository.findByActivityId(activityId);

        return activityRecommendations.stream()
                .map(this::mapToActivityRecommendationResponse)
                .collect(Collectors.toList());
    }
}
