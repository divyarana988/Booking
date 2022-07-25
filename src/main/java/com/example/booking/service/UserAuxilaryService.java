package com.example.booking.service;

import com.example.booking.entity.User;
import com.example.booking.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuxilaryService {
    @Autowired
    private UserService userService;

    public UserAuxilaryService(UserService userService){
        this.userService = userService;
    }

    public User findUserByUserName(String userName) throws UserNotFoundException {
        return this.userService.findUserByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
    }

    public User addUser( User user) throws IllegalArgumentException {
        return this.userService.addUser(user);
    }

}
