package com.mail_sentinal.mail_sentinal_backend.service;

import com.mail_sentinal.mail_sentinal_backend.ml.MLServiceClient;
import com.mail_sentinal.mail_sentinal_backend.model.Email;
import com.mail_sentinal.mail_sentinal_backend.model.FraudResult;
import com.mail_sentinal.mail_sentinal_backend.repository.EmailRepository;
import com.mail_sentinal.mail_sentinal_backend.repository.FraudResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class EmailProcessingService {

    private final EmailRepository emailRepository;
    private final MLServiceClient mlServiceClient;
    private final FraudResultRepository fraudResultRepository;



    public void processLatestEmails() {

        List<Email> emails = emailRepository.findTop60ByOrderByMailReceivedAtDesc();

        for (Email email : emails) {

            FraudResult result = mlServiceClient.analyzeEmail(email);

            fraudResultRepository.save(result);
        }
    }

}
