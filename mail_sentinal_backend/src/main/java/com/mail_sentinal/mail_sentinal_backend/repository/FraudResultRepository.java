package com.mail_sentinal.mail_sentinal_backend.repository;

import com.mail_sentinal.mail_sentinal_backend.model.FraudResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FraudResultRepository extends JpaRepository<FraudResult,Long> {
}
