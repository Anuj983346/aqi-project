package com.example.aqi.controller;

import com.example.aqi.service.AqiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AqiController {
    private final AqiService aqiService;

    public AqiController(AqiService aqiService) {
        this.aqiService = aqiService;
    }

    @GetMapping("/api/aqi")
    public ResponseEntity<?> getAqi(@RequestParam String city) {
        Map<String,Object> result = aqiService.fetchAqiForCity(city);
        return ResponseEntity.ok(result);
    }
}
