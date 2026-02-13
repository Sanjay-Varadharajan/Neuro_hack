package com.mail_sentinal.mail_sentinal_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Email {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long mailId;

    private String senderMail;

    private String mailSubject;

    @Column(columnDefinition = "TEXT")
    private String mailBody;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime mailReceivedAt;

}
