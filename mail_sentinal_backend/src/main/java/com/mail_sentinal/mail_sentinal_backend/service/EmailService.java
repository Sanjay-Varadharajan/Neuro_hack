    package com.mail_sentinal.mail_sentinal_backend.service;


    import com.mail_sentinal.mail_sentinal_backend.model.Email;
    import com.mail_sentinal.mail_sentinal_backend.model.FraudResult;
    import com.mail_sentinal.mail_sentinal_backend.repository.EmailRepository;
    import com.mail_sentinal.mail_sentinal_backend.repository.FraudResultRepository;
    import com.mail_sentinal.mail_sentinal_backend.sms.SmsService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Service;
    import org.springframework.web.client.RestTemplate;

    import com.mail_sentinal.mail_sentinal_backend.ml.MLServiceClient;


    import javax.mail.*;
    import javax.mail.search.FlagTerm;
    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Properties;


    @Service
    @RequiredArgsConstructor
    public class EmailService {

        private final EmailRepository emailRepository;

        private final FraudResultRepository fraudResultRepository;

        private final MLServiceClient mlServiceClient;

        private final SmsService smsService;


        @Value("${gmail.username}")
        private String username;

        @Value("${gmail.password}")
        private String password;

        @Value("${gmail.host}")
        private String host;

        @Value("${gmail.port}")
        private String port;

        private final RestTemplate restTemplate = new RestTemplate();

        public List<Email> fetchNewEmailsFromInbox() {
            List<Email> newEmails = new ArrayList<>();

            try {
                Properties props = new Properties();
                props.put("mail.store.protocol", "imaps");
                props.put("mail.imaps.host", host);
                props.put("mail.imaps.port", "993");
                props.put("mail.imaps.ssl.enable", "true");
                props.put("mail.imaps.ssl.protocols", "TLSv1.2");
                props.put("mail.imaps.ssl.trust", "*");
                props.put("mail.imaps.starttls.enable", "true");

                Session session = Session.getInstance(props);
                Store store = session.getStore("imaps");
                store.connect(username, password);

                Folder inbox = store.getFolder("INBOX");
                inbox.open(Folder.READ_WRITE);

                // Fetch unseen emails
                Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

                for (Message message : messages) {

                    String sender = message.getFrom()[0].toString();
                    String subject = message.getSubject();
                    String body = extractTextFromMessage(message);

                    Email email = new Email();
                    email.setSenderMail(sender);
                    email.setMailSubject(subject);
                    email.setMailBody(body);

                    emailRepository.save(email);
                    newEmails.add(email);

                    processEmail(email);

                    message.setFlag(Flags.Flag.SEEN, true);
                }


                inbox.close(false);
                store.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return newEmails;
        }


        public void processEmail(Email email) {
            try {
                FraudResult result = mlServiceClient.analyzeEmail(email);

                result.setFraud(result.getConfidence() >= 0.75);
                result.setAnalyzedAt(LocalDateTime.now());


                // Save fraud result to DB
                fraudResultRepository.save(result);


                if (result.isFraud()) {
                    String smsBody = "⚠ Suspicious Email!\n" +
                            "From: " + email.getSenderMail() + "\n" +
                            "Subject: " + email.getMailSubject() + "\n" +
                            "Confidence: " + result.getConfidence() + "\n" +
                            "Threat: "+" confirmation";
                    smsService.sendSms("+917598077190", smsBody);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        private String extractTextFromMessage(Message message) throws Exception {

            if (message.isMimeType("text/plain")) {
                return message.getContent().toString();
            }

            if (message.isMimeType("multipart/*")) {
                Multipart multipart = (Multipart) message.getContent();
                return getTextFromMultipart(multipart);
            }

            return "";
        }




        private String getTextFromMultipart(Multipart multipart) throws Exception {

            StringBuilder result = new StringBuilder();

            int count = multipart.getCount();

            for (int i = 0; i < count; i++) {

                BodyPart bodyPart = multipart.getBodyPart(i);

                if (bodyPart.isMimeType("text/plain")) {
                    result.append(bodyPart.getContent().toString());
                }

                else if (bodyPart.isMimeType("text/html")) {
                    String html = bodyPart.getContent().toString();
                    String plainText = html.replaceAll("<[^>]*>", "");
                    result.append(plainText);
                }

                else if (bodyPart.getContent() instanceof Multipart) {
                    result.append(getTextFromMultipart((Multipart) bodyPart.getContent()));
                }
            }

            return result.toString();
        }

    }
