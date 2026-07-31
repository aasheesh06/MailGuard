package com.aash.mailguard.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "email_validation_history")
public class EmailValidationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String domain;

    private Boolean formatValid;

    private Boolean mxRecordFound;

    private Boolean smtpVerified;

    private String status;

    private String message;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public EmailValidationHistory() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Boolean getFormatValid() {
        return formatValid;
    }

    public void setFormatValid(Boolean formatValid) {
        this.formatValid = formatValid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getMxRecordFound() {
        return mxRecordFound;
    }

    public void setMxRecordFound(Boolean mxRecordFound) {
        this.mxRecordFound = mxRecordFound;
    }

    public Boolean getSmtpVerified() {
        return smtpVerified;
    }

    public void setSmtpVerified(Boolean smtpVerified) {
        this.smtpVerified = smtpVerified;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
