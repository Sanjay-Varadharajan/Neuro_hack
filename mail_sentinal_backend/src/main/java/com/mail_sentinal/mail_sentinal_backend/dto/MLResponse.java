package com.mail_sentinal.mail_sentinal_backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MLResponse {

    private String prediction;
    private double confidence;
    private String riskLevel;
    private List<String> suspiciousIndicators;
    private Map<String, Double> probabilities;
    private String sender;
    private String timestamp;
}
