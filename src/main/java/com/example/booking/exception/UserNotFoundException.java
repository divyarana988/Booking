package com.example.booking.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String msg){
        super(msg);
    }

    public UserNotFoundException(String msg, Throwable cause){
        super(msg, cause);
    }
}
