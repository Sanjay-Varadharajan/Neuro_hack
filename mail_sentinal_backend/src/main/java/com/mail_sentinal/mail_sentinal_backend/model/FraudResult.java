    package com.mail_sentinal.mail_sentinal_backend.model;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;


    import java.time.LocalDateTime;

    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class FraudResult {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long fraudResultId;

        @OneToOne
        @JoinColumn(name = "email_id")
        private Email email;

        private String prediction;

        private double confidence;

        private String riskLevel;

        @Column(length = 2000)
        private String suspiciousIndicators;

        @Column(length = 1000)
        private String probabilities;

        private String mlTimestamp;

        @Column(updatable = false)
        private LocalDateTime analyzedAt;

        @Column(nullable = false)
        private boolean isFraud = false;


    }
