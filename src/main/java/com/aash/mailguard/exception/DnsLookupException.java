package com.aash.mailguard.exception;

public class DnsLookupException extends RuntimeException{
    public DnsLookupException(String message) {
        super(message);
    }

    public DnsLookupException(String message, Throwable cause) {
        super(message, cause);
    }
}
