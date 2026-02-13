package com.mail_sentinal.mail_sentinal_backend.repository;

import com.mail_sentinal.mail_sentinal_backend.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<Email,Long> {

    List<Email> findTop60ByOrderByMailReceivedAtDesc();

}
