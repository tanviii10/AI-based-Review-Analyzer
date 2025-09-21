package com.spring.AiReviewAnalysis.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.AiReviewAnalysis.responces.AIResult;

@Controller
public class AiController {
	
	@Value("${openai.api.key}")
    private String API_KEY;

    @GetMapping("/test")
    public String test() {
        return "Spring Boot AI Review Analysis is running!";
    }
    
    @GetMapping("/reviewForm")
    public String reviewForm() {
        return "reviewForm"; 
    }

    @PostMapping("/analyzeReviewForm")
    public String analyzeReview(@RequestParam String prompt, Model model) {
        String rawResponse = callOpenAi(prompt);

      
        String sentiment = "N/A";
        String mood = "N/A";
        String personality = "N/A";
        String conclusion = rawResponse;

        try {
            JSONObject obj = new JSONObject(rawResponse);
            if (obj.has("sentiment")) sentiment = obj.getString("sentiment");
            if (obj.has("mood")) mood = obj.getString("mood");
            if (obj.has("personality")) personality = obj.getString("personality");
            if (obj.has("conclusion")) conclusion = obj.getString("conclusion");
        } catch (Exception e) {
            
        }

        model.addAttribute("sentiment", sentiment);
        model.addAttribute("mood", mood);
        model.addAttribute("personality", personality);
        model.addAttribute("conclusion", conclusion);
        model.addAttribute("prompt", prompt);

        return "reviewForm";
    }

    private String callOpenAi(String promptText) {
        try {
            String endpoint = "https://api.openai.com/v1/chat/completions";

            String fullPrompt = "Analyze this review:\n" + promptText +
                    "\nReturn JSON with: sentiment, mood, personality, conclusion.";

            JSONObject requestJson = new JSONObject()
                    .put("model", "gpt-3.5-turbo")
                    .put("messages", new JSONArray()
                            .put(new JSONObject()
                                    .put("role", "user")
                                    .put("content", fullPrompt)))
                    .put("max_tokens", 200);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(requestJson.toString()))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject obj = new JSONObject(response.body());
            return obj.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
