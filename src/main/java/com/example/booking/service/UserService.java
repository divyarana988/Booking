package com.example.booking.service;

import com.example.booking.entity.User;
import com.example.booking.enums.UserType;
import com.example.booking.exception.UserNotFoundException;
import com.example.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void save(User user){
        this.userRepository.save(user);
    }

    public Optional<User> findUserByUserName(String userName){
        return this.userRepository.findUserByUserName(userName);
    }

    private String getUserName(User user) {
        return (user.getFirstName() + user.getLastName());
    }

    public User addUser(User user) throws IllegalArgumentException {
        try {
            User newUser = user;
            validateInput(newUser);
            String userName = getUserName(user).replaceAll("\\s", "");
            newUser.setUserName(userName);
            newUser.setCreatedOn(LocalDateTime.now());
            this.save(newUser);
            return newUser;
        }  catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
            // return e;
        }
    }

    private void validateInput(User user) {
        try {
            Assert.notNull(user, "User object must not be null");
            Assert.hasLength(user.getFirstName(), "User first name must not be null or empty");
            Assert.hasLength(user.getLastName(), "User last name must not be null or empty");
            Assert.hasLength(user.getMobileNumber(), "User mobile number must not be empty");
            Assert.hasLength(user.getUserType().toString(), "User type must not be null or empty");
            Assert.hasLength(user.getEmail(), "Email must not be null or empty");
            Assert.hasLength(user.getPassword(), "password must not be null or empty");
            Assert.isTrue(user.getMobileNumber().length() == 10, "Invalid mobile number");
            switch (user.getUserType().toString().toUpperCase()) {
                case "ADMIN":
                    user.setUserType(UserType.ADMIN);
                    break;
                case "USER":
                    user.setUserType(UserType.USER);
                    break;
                default:
                    Assert.isTrue(false, "Only values allowed for user type is : Admin or Normal");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
