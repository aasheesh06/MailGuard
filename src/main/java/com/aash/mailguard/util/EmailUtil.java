package com.aash.mailguard.util;

import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
    public String extractDomain(String email){

        int atIndex=email.indexOf("@");
        String domainName=email.substring(atIndex+1);
        return domainName;
    }
}
