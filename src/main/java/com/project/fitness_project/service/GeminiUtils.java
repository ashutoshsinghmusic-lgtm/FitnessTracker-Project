package com.project.fitness_project.service;

import com.project.fitness_project.model.Activity;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Data
public class GeminiUtils {


    public String buildPrompt(Activity activity) {
        return """
                You are a fitness expert . Provide Recommendations For the 
                following activities.
       
 
                Activity Type: %s
                Duration: %d minutes
                Calories Burned: %d
 
                Respond ONLY in this EXACT JSON format. Do not write any additional text:
 
                {
                  "improvements": ["point 1", "point 2"],
                  "suggestions": ["point 1", "point 2"],
                  "safety": ["point 1", "point 2"]
                }
                """.formatted(activity.getType(), activity.getDuration(), activity.getCaloriesBurned());
    }



    public Map<String, List<String>> parseAiResponse(String aiText) {


        String cleanJson = aiText.substring(aiText.indexOf("{"), aiText.lastIndexOf("}") + 1);

        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            return mapper.readValue(cleanJson, Map.class);
        } catch (Exception e) {

            Map<String, List<String>> fallback = new HashMap<>();
            fallback.put("improvements", List.of("Recommendations Could not be generated , try again"));
            fallback.put("suggestions", List.of());
            fallback.put("safety", List.of());
            return fallback;
        }
    }


    public String extractTextFromResponse(Map response) {
        List candidates = (List) response.get("candidates");
        Map firstCandidate = (Map) candidates.get(0);
        Map content = (Map) firstCandidate.get("content");
        List parts = (List) content.get("parts");
        Map firstPart = (Map) parts.get(0);
        return (String) firstPart.get("text");
    }

}
