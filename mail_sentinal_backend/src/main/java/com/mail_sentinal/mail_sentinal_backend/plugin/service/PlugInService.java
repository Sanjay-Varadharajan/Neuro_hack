package com.mail_sentinal.mail_sentinal_backend.plugin.service;

import com.mail_sentinal.mail_sentinal_backend.model.FraudResult;
import com.mail_sentinal.mail_sentinal_backend.repository.FraudResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlugInService{

    private final FraudResultRepository fraudResultRepository;

    public List<FraudResult> viewAllResult() {
        return fraudResultRepository.findAll();
    }
}
