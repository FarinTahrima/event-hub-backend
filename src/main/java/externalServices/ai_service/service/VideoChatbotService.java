package externalServices.ai_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VideoChatbotService {

    @Value("${google.api.secret}")
    private String googleApiKey;

    private final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";
//    private final String PRE_PROMPT = "You are an AI assistant that analyzes Steamboat Willie. Make it short and concise " +
//            "Respond to the user's input, but make sure your response is always about Steamboat Willie. " ;

    private final String PRE_PROMPT = """                     
            You are an AI assistant that private final String PRE_PROMPT = You are an AI assistant that specializes in our company's latest laptop release.
            You are participating in a live sales event and must provide enthusiastic, accurate, and concise responses about the laptop's features, specifications, and value proposition. Keep responses professional yet engaging, focusing on technical details and user benefits.
            If asked about competitors or topics unrelated to our laptop, politely redirect the conversation back to our product. 
            If you don't know specific details, acknowledge that and focus on the features you do know. 
            Keep responses brief and sales-oriented while maintaining authenticity. 
            Avoid making price commitments or promises about future features. 
            Always maintain a helpful, positive tone appropriate for a professional sales environment.
            """;


    public String generateResponse(String userInput) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        List<Map<String, Object>> contents = new ArrayList<>();

        // Add pre-prompt with role "model"
        Map<String, Object> prePromptContent = new HashMap<>();
        prePromptContent.put("role", "model");
        Map<String, String> prePromptPart = new HashMap<>();
        prePromptPart.put("text", PRE_PROMPT);
        prePromptContent.put("parts", new Object[]{ prePromptPart });
        contents.add(prePromptContent);

        // Add user input with role "user"
        Map<String, Object> userContent = new HashMap<>();
        userContent.put("role", "user");
        Map<String, String> userPart = new HashMap<>();
        userPart.put("text", userInput);
        userContent.put("parts", new Object[]{ userPart });
        contents.add(userContent);

        requestBody.put("contents", contents);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        String url = GEMINI_API_URL + "?key=" + googleApiKey;

        try {
            Map<String, Object> response = restTemplate.postForObject(url, request, Map.class);
            return extractGeneratedText(response);
        } catch ( Exception e ) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }


    private String extractGeneratedText(Map<String, Object> response) {
        if ( response == null || !response.containsKey("candidates") ) {
            return "Error: Unexpected response format";
        }

        List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
        if ( candidates.isEmpty() ) {
            return "Error: No response generated";
        }

        Map<String, Object> firstCandidate = candidates.get(0);
        if ( !firstCandidate.containsKey("content") ) {
            return "Error: Response content not found";
        }

        Map<String, Object> content = (Map<String, Object>) firstCandidate.get("content");
        List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
        if ( parts.isEmpty() ) {
            return "Error: Response parts not found";
        }

        return (String) parts.get(0).get("text");
    }
}



