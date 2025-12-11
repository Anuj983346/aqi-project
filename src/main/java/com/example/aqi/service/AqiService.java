package com.example.aqi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.HashMap;
import java.util.Map;

@Service
public class AqiService {
    private final WebClient webClient;
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${aqi.api.url:}")
    private String apiUrlTemplate;

    @Value("${aqi.api.key:}")
    private String apiKey;

    public AqiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Map<String,Object> fetchAqiForCity(String city) {
        Map<String,Object> responseMap = new HashMap<>();
        if (apiUrlTemplate == null || apiUrlTemplate.isBlank()) {
            responseMap.put("error", "AQI API URL not configured. Set aqi.api.url in application.properties");
            return responseMap;
        }

        // Replace placeholders {city} and {API_KEY} if present
        String url = apiUrlTemplate.replace("{city}", city).replace("{API_KEY}", apiKey == null ? "" : apiKey);

        try {
            String body = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode node = mapper.readTree(body);
            responseMap.put("source", node);
            // try to extract some common aqi fields if present (waqi format)
            if (node.has("data") && node.get("data").has("aqi")) {
                responseMap.put("aqi", node.get("data").get("aqi").asInt());
            }
        } catch (WebClientResponseException ex) {
            responseMap.put("error", "Upstream error: " + ex.getStatusCode());
            responseMap.put("details", ex.getResponseBodyAsString());
        } catch (Exception ex) {
            responseMap.put("error", "Exception: " + ex.getMessage());
        }
        return responseMap;
    }
}
