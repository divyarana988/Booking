package com.example.booking.util;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class SimpleResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private int statusCode;
    private String message;


    public SimpleResponse build(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        return this;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseHelper [statusCode=" + statusCode + ", message=" + message + "]";
    }
}
