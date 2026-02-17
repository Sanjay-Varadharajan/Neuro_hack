package com.mail_sentinal.mail_sentinal_backend.sms;


import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;


@Service
@RequiredArgsConstructor
public class SmsService {


    @Value("${twilio.account.sid}")
    private String ACCOUNT_SID;

    @Value("${twilio.auth.token}")
    private String AUTH_TOKEN;

    @Value("${twilio.phone.number}")
    private String FROM_NUMBER;




    @Async("smsExecutor")
    public void sendSms(String to,String messageBody){
        Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(FROM_NUMBER),
                messageBody
        ).create();
    }




}

