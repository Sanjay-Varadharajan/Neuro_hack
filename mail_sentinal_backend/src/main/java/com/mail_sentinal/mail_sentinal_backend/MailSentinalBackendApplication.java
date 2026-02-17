package com.mail_sentinal.mail_sentinal_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@EnableAsync
public class MailSentinalBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailSentinalBackendApplication.class, args);
	}

}
