package com.mail_sentinal.mail_sentinal_backend.controller;

import com.mail_sentinal.mail_sentinal_backend.model.FraudResult;
import com.mail_sentinal.mail_sentinal_backend.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {


    private final AnalyticsService analyticsService;


    @GetMapping("/viewall")
    public ResponseEntity<List<Map<String,Object>>> viewAllAnalytics(){
        List<FraudResult> fraudResult=analyticsService.viewAllAnalytics();

        List<Map<String, Object>> response = fraudResult.stream().map(f -> {
            Map<String, Object> map = new HashMap<>();
            map.put("sender", f.getEmail() != null ? f.getEmail().getSenderMail() : "UNKNOWN");
            map.put("prediction", f.getPrediction());
            map.put("confidence", f.getConfidence());
            map.put("riskLevel", f.getRiskLevel());
            map.put("suspiciousIndicators", f.getSuspiciousIndicators()); // list
            map.put("probabilities", f.getProbabilities()); // json string
            map.put("mlTimestamp", f.getMlTimestamp());
            map.put("analyzedAt", f.getAnalyzedAt());
            return map;
        }).toList();

        return ResponseEntity.ok(response);
    }

}
