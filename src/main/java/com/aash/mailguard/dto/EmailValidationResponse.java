package com.aash.mailguard.dto;

public class EmailValidationResponse {
    private String email;
    private String domain;
    private Boolean formatValid;
    private Boolean mxRecordFound;
    private Boolean smtpVerified;
    private String status;
    private String message;

    public EmailValidationResponse() {
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getFormatValid() {
        return formatValid;
    }

    public void setFormatValid(Boolean formatValid) {
        this.formatValid = formatValid;
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
