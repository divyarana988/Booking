package com.example.booking.controller;

import com.example.booking.Request.UpdateUserRequest;
import com.example.booking.entity.User;
import com.example.booking.exception.BasicException;
import com.example.booking.service.UserAuxilaryService;
import com.example.booking.util.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1")
@Component
@Configuration
public class UserController {

    @Autowired
    private UserAuxilaryService userAuxilaryService;


    @GetMapping(value = "/getUserDetails")
    public ResponseEntity<Object> getUserDetails(@RequestParam("userName") String userName) throws BasicException {
        return this.userAuxilaryService.findUserByUserName(userName);
    }

    @PostMapping(value = "/addUser")
    public SimpleResponse addUser(@RequestBody User user) throws IllegalArgumentException, Exception {
        return this.userAuxilaryService.addUser(user);
    }

    @PutMapping(value="/updateUser")
    public SimpleResponse updateUser(String userName, @RequestBody UpdateUserRequest request){
        return this.userAuxilaryService.updateUser(userName, request);
    }

}

