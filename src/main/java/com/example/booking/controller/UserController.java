package com.example.booking.controller;

import com.example.booking.entity.User;
import com.example.booking.exception.UserNotFoundException;
import com.example.booking.service.UserAuxilaryService;
import com.example.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public User getUserDetails(@RequestParam("userName") String userName) throws UserNotFoundException {
        return this.userAuxilaryService.findUserByUserName(userName);
    }

    @PostMapping(value = "/addUser")
    public User addUser(@RequestBody User user) throws IllegalArgumentException {
        return this.userAuxilaryService.addUser(user);
    }

}

