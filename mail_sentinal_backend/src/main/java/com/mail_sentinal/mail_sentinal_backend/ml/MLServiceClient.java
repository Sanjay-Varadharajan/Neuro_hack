package com.mail_sentinal.mail_sentinal_backend.ml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mail_sentinal.mail_sentinal_backend.dto.MLResponse;
import com.mail_sentinal.mail_sentinal_backend.model.Email;
import com.mail_sentinal.mail_sentinal_backend.model.FraudResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MLServiceClient{

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${ml.service.url}")
    private String mlServiceUrl;

    public FraudResult analyzeEmail(Email email) {

        Map<String, Object> request = new HashMap<>();
        request.put("sender", email.getSenderMail());
        request.put("subject", email.getMailSubject());
        request.put("body", email.getMailBody());

        try {

            ResponseEntity<MLResponse> response =
                    restTemplate.postForEntity(
                            mlServiceUrl,
                            request,
                            MLResponse.class
                    );

            MLResponse ml = response.getBody();

            FraudResult result = new FraudResult();
            result.setEmail(email);

            if (ml != null) {
                result.setPrediction(ml.getPrediction());
                result.setConfidence(ml.getConfidence());
                result.setRiskLevel(ml.getRiskLevel());
                result.setMlTimestamp(ml.getTimestamp());

                // Convert List and Map to JSON string
                result.setSuspiciousIndicators(
                        objectMapper.writeValueAsString(ml.getSuspiciousIndicators())
                );

                result.setProbabilities(
                        objectMapper.writeValueAsString(ml.getProbabilities())
                );
            }

            return result;

        } catch (Exception e) {

            FraudResult fallback = new FraudResult();
            fallback.setEmail(email);
            fallback.setPrediction("unknown");
            fallback.setConfidence(0.0);
            fallback.setRiskLevel("ML Service Unavailable");

            return fallback;
        }
    }
}
