package com.mail_sentinal.mail_sentinal_backend.sms;


import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;


@Service
public class SmsService {


    private final String ACCOUNT_SID = "AC32da030cd4dbf4e5df295f0162ec7e65";
    private final String AUTH_TOKEN = "44a2042a9f6615a9df5bfe67e616eae1";
    private final String FROM_NUMBER = "+18303645858";

    public SmsService() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendSms(String to,String messageBody){
        Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(FROM_NUMBER),
                messageBody
        ).create();
    }




}

