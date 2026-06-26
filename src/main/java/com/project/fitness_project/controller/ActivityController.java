package com.project.fitness_project.controller;

import com.project.fitness_project.dto.ActivityRequest;
import com.project.fitness_project.dto.ActivityResponse;
import com.project.fitness_project.service.ActivityService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    public final ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityResponse> saveActivities(@RequestBody ActivityRequest request){
        ActivityResponse response =  activityService.saveActivities(request);
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getAllActivitiesById(){
        return ResponseEntity.ok(activityService.getAllActivitiesById());
    }

}
