package com.mail_sentinal.mail_sentinal_backend.utils;


import com.mail_sentinal.mail_sentinal_backend.model.Email;
import com.mail_sentinal.mail_sentinal_backend.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailFetchScheduler {

    private final EmailService emailService;

    @Scheduled(fixedDelay = 1000)
    public void fetchAndProcessEmails() {
        emailService.fetchNewEmailsFromInbox();
    }
}
