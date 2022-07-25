package com.example.booking.exception;

public class ThreatreNotFoundException extends RuntimeException{
    public ThreatreNotFoundException(String msg){
        super(msg);
    }
    public ThreatreNotFoundException(String msg, Throwable cause){
        super(msg, cause);
    }
}
