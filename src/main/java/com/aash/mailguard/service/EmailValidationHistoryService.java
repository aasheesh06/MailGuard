package com.aash.mailguard.service;

import com.aash.mailguard.dns.DnsLookupService;
import com.aash.mailguard.dto.EmailValidationRequest;
import com.aash.mailguard.dto.EmailValidationResponse;
import com.aash.mailguard.entity.EmailValidationHistory;
import com.aash.mailguard.repository.EmailValidationHistoryRepository;
import com.aash.mailguard.smtp.SmtpVerificationService;
import com.aash.mailguard.util.EmailUtil;
import com.aash.mailguard.validator.EmailFormatValidator;
import org.springframework.stereotype.Service;


@Service
public class EmailValidationHistoryService {

    private final EmailValidationHistoryRepository repository;
    private final EmailFormatValidator validator;
    private final DnsLookupService dnsLookup;
    private final EmailUtil util;
    private final SmtpVerificationService smtpVerificationService;

    public EmailValidationHistoryService(
            EmailValidationHistoryRepository repository,
            EmailFormatValidator validator,
            DnsLookupService dnsLookup,
            EmailUtil util,SmtpVerificationService smtpVerificationService) {

        this.repository = repository;
        this.validator=validator;
        this.dnsLookup=dnsLookup;
        this.util=util;
        this.smtpVerificationService=smtpVerificationService;
    }
    public EmailValidationResponse validate(EmailValidationRequest request) {

        String email = request.getEmail();

        EmailValidationResponse response = new EmailValidationResponse();
        response.setEmail(email);


        boolean formatValid = validator.isFormatValid(email);
        response.setFormatValid(formatValid);

        if (!formatValid){
            response.setStatus("INVALID");
            response.setMessage("Invalid email format");
            saveHistory(response);
            return response;
        }


        String domain= util.extractDomain(email);
        response.setDomain(domain);


        boolean mxFound=dnsLookup.hasMxRecord(domain);
        response.setMxRecordFound(mxFound);

        if (!mxFound) {
            response.setStatus("INVALID");
            response.setMessage("Domain has no MX Record");
            saveHistory(response);
            return response;
        }



        String mxHost = dnsLookup.getMxHost(domain);

        if (mxHost == null) {
            response.setStatus("INVALID");
            response.setMessage("MX Host not found");
            saveHistory(response);
            return response;
        }

        boolean smtpVerified =
                smtpVerificationService.verify(email, mxHost);

        response.setSmtpVerified(smtpVerified);

        if (smtpVerified) {
            response.setStatus("VALID");
            response.setMessage("Email verified successfully.");
        } else {
            response.setStatus("UNVERIFIED");
            response.setMessage("SMTP verification failed.");
        }

        response.setMessage("Email format and domain are valid.");


        saveHistory(response);

        return response;
    }

    private void saveHistory(EmailValidationResponse response) {
        EmailValidationHistory history = new EmailValidationHistory();

        history.setEmail(response.getEmail());
        history.setDomain(response.getDomain());
        history.setFormatValid(response.getFormatValid());
        history.setMxRecordFound(response.getMxRecordFound());
        history.setSmtpVerified(response.getSmtpVerified());
        history.setStatus(response.getStatus());
        history.setMessage(response.getMessage());

        repository.save(history);
    }


}