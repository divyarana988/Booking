package com.example.booking.exception;

import com.example.booking.util.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class BasicException extends RuntimeException{

    @Autowired
    private SimpleResponse simpleResponse;

   public BasicException(String err){
       super(err);
   }
}
