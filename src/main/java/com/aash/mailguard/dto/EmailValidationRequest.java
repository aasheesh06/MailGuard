package com.aash.mailguard.dto;

public class EmailValidationRequest {
    private  String email;

    public EmailValidationRequest() {
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
