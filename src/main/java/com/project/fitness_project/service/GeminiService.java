package com.project.fitness_project.service;

import com.project.fitness_project.model.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeminiService {

    private final GeminiUtils geminiUtils;

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestClient restClient = RestClient.create();

    private static final String GEMINI_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent";


    public Map<String, List<String>> generateRecommendation(Activity activity) {


        String prompt = geminiUtils.buildPrompt(activity);

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                )
        );


        Map<String, Object> response = restClient.post()
                .uri(GEMINI_URL)
                .header("x-goog-api-key", apiKey)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        if(response == null)
                throw new RuntimeException("Can't Generate Recommendations");

        String aiText = geminiUtils.extractTextFromResponse(response);


        return geminiUtils.parseAiResponse(aiText);
    }


}
