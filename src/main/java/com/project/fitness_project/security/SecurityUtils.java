package com.project.fitness_project.security;

import com.project.fitness_project.model.Activity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import org.springframework.security.access.AccessDeniedException;

@Component
public class SecurityUtils {

    public String getCurrentUserId(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String userId = customUserDetails.getId();

        return userId;

    }


    public boolean isActivityBelongsToCurrentUser(Activity activity , String userId) throws AccessDeniedException {

       if(!userId.equals(activity.getUser().getId())){
           throw new AccessDeniedException("This activity dose not belong to you");
        }

       return true;
    }
}
