package com.aash.mailguard.repository;

import com.aash.mailguard.entity.EmailValidationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailValidationHistoryRepository
        extends JpaRepository<EmailValidationHistory, Long> {


}