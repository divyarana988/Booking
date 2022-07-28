package com.example.booking.service;

import com.example.booking.Request.UpdateUserRequest;
import com.example.booking.entity.User;
import com.example.booking.exception.BasicException;
import com.example.booking.exception.UserNotFoundException;
import com.example.booking.util.SimpleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserAuxilaryService {
    @Autowired
    private UserService userService;

    @Autowired
    private SimpleResponse simpleResponse;

    public UserAuxilaryService(UserService userService, SimpleResponse simpleResponse){
        this.userService = userService;
        this.simpleResponse = simpleResponse;
    }

    public ResponseEntity<Object> findUserByUserName(String userName) throws BasicException {
         try {
            User user = this.userService.findUserByUserNameFromDb(userName);
            if(user==null){
                return new ResponseEntity<>(this.simpleResponse.build(HttpStatus.BAD_REQUEST.value(), "User does not exist"), HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(this.simpleResponse.build(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
         /*
        User userFromdb = this.userService.findUserByUserNameFromDb(userName);
        if(userFromdb == null){
            throw new BasicException("User does not exist");
        }else{
            return userFromdb;
        }

          */
    }

    public SimpleResponse addUser(User user) throws IllegalArgumentException, Exception {
            User found = this.userService.findUserByUserNameFromDb(user.getFirstName()+user.getLastName());
            if(found!=null){
                return this.simpleResponse.build(HttpStatus.BAD_REQUEST.value(), "User already exist");
            }else{
                return this.userService.addUser(user);
            }
    }

    public SimpleResponse updateUser(String userName, UpdateUserRequest request) {
        User userFound = this.userService.findUserByUserNameFromDb(userName);
        if(userFound==null){
            return this.simpleResponse.build(HttpStatus.BAD_REQUEST.value(), "User do not exist");
        }else{
            return this.userService.updateUser(userName, request);
        }
    }
}
