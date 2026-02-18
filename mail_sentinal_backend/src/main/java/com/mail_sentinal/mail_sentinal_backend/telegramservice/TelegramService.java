package com.mail_sentinal.mail_sentinal_backend.telegramservice;

import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class TelegramService {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.chat.id}")
    private String chatId;


    private final RestTemplate restTemplate=new RestTemplate();

    public void sendMessage(String message){

        if (message == null || message.trim().isEmpty()) {
            System.out.println("Telegram message is empty. Skipping.");
            return;
        }

        String url = "https://api.telegram.org/bot"+botToken+
                "/sendMessage";
        Map<String, String> body = new HashMap<>();
        body.put("chat_id", chatId);
        body.put("text", message);

        restTemplate.postForObject(url,body, String.class);

    }
}
