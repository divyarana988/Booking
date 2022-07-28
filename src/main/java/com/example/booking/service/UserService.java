package com.example.booking.service;

import com.example.booking.Request.UpdateUserRequest;
import com.example.booking.entity.User;
import com.example.booking.enums.UserType;
import com.example.booking.exception.UserNotFoundException;
import com.example.booking.repository.UserRepository;
import com.example.booking.util.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private static final Pattern VALID_EMAIL_ADDRESS = Pattern
            .compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private SimpleResponse simpleResponse;


    public UserService(UserRepository userRepository, SimpleResponse simpleResponse){
        this.userRepository = userRepository;
        this.simpleResponse = simpleResponse;
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

    public SimpleResponse addUser(User user) throws IllegalArgumentException, Exception, UserNotFoundException{
        try {
            User newUser = user;
            validateInput(newUser);
            String userName = getUserName(user).replaceAll("\\s", "");
            newUser.setUserName(userName);
            newUser.setCreatedOn(LocalDateTime.now());
            this.save(newUser);
            return this.simpleResponse.build(HttpStatus.CREATED.value(), "Successfully saved user with user name: "+newUser.getUserName());
        }  catch (IllegalArgumentException e) {
            return this.simpleResponse.build(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    private  static final Pattern Valid_Password = Pattern.compile("^[A-Z]+[a-z]+[0-9]+[A-Z]$", Pattern.CASE_INSENSITIVE );
    private void validateInput(User user) {
        try {
            Assert.notNull(user, "User object must not be null");
            Assert.hasLength(user.getFirstName(), "User first name must not be null or empty");
            Assert.hasLength(user.getLastName(), "User last name must not be null or empty");
            Assert.hasLength(user.getMobileNumber(), "User mobile number must not be empty");
            Assert.hasLength(user.getUserType().toString(), "User type must not be null or empty");
            Assert.hasLength(user.getEmail(), "Email must not be null or empty");
            Assert.hasLength(user.getPassword(), "password must not be null or empty");
         //   if(user.getPassword().contains(any()));
            Matcher matchPassword = Valid_Password.matcher(user.getPassword());
            Assert.isTrue(matchPassword.find(), "invalid passowrd");
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
            Matcher matcher = VALID_EMAIL_ADDRESS.matcher(user.getEmail());
            Assert.isTrue(matcher.find(), "Invalid email id");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    public User findUserByUserNameFromDb(String userName){
        return this.userRepository.getFromdb(userName);
    }

    public SimpleResponse updateUser(String userName, UpdateUserRequest request) {
        User userFromDb = this.findUserByUserNameFromDb(userName);
        if(request.getEmail()!=null){
            userFromDb.setEmail(request.getEmail());
        }
        if(request.getMobileNumber()!=null){
            userFromDb.setMobileNumber(request.getMobileNumber());
        }
        if(request.getFirstName()!=null){
            userFromDb.setFirstName(request.getFirstName());
        }
        if(request.getLastName()!=null){
            userFromDb.setLastName(request.getLastName());
        }
        if(request.getPassword()!=null){
            userFromDb.setPassword(request.getPassword());
        }
        this.save(userFromDb);
        return this.simpleResponse.build(HttpStatus.OK.value(), "User updated successfully");
    }
}
