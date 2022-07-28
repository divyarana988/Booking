package com.example.booking.service;

import com.example.booking.Request.UpdateThreatreRequest;
import com.example.booking.entity.Threatre;
import com.example.booking.entity.User;
import com.example.booking.enums.UserType;
import com.example.booking.exception.ThreatreNotFoundException;
import com.example.booking.repository.ThreatreRepository;
import com.example.booking.util.SimpleResponse;
import org.eclipse.jetty.client.HttpSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ThreatreService {
    @Autowired
    private ThreatreRepository threatreRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private SimpleResponse simpleResponse;

    @Autowired
    public ThreatreService(ThreatreRepository threatreRepository, UserService userService, SimpleResponse simpleResponse){
        this.threatreRepository = threatreRepository;
        this.userService = userService;
        this.simpleResponse = simpleResponse;
    }

    public SimpleResponse addThreatre(Threatre theater) throws IllegalArgumentException {
        try {
            validateInput(theater);
            User user = this.userService.findUserByUserNameFromDb(theater.getUserName());
            if(user.getUserType() != UserType.ADMIN){
                return this.simpleResponse.build(HttpStatus.BAD_REQUEST.value(), "Only admin can add threatre");
            }else{
                Threatre newTheater = theater;
                String theaterId = newTheater.getName().replaceAll("\\s", "");
                newTheater.setThreatreId(theaterId);
                newTheater.setCreatedOn(LocalDateTime.now());
                this.threatreRepository.save(newTheater);
                return this.simpleResponse.build(HttpStatus.OK.value(), "Successfully saved threatre with threatreId: " + newTheater.getThreatreId());

            }
        } catch (IllegalArgumentException e) {
            return this.simpleResponse.build(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            // return e;
        }
    }

    public List<String> getSupportedCities() throws ThreatreNotFoundException {
        List<String> fromDb = this.getFromDb();
        if(fromDb.isEmpty()){
            return new ArrayList<>();
        }else{
            return fromDb;
        }
    }

    private List<String> getFromDb() {
        return this.threatreRepository.getSupportedCities();
    }

    private void validateInput(Threatre theater) {
        try {
            Assert.notNull(theater, "User object must not be null");
            Assert.hasLength(theater.getName(), "theater name must not be null or empty");
            Assert.hasLength(theater.getAddress(), "theater address must not be null or empty");
            Assert.hasLength(theater.getCity(), "theater city must not be null or empty");
            Assert.hasLength(theater.getLanguages(), "theater language must not be null or empty");
            Assert.hasLength(theater.getUserName(), "theater user name must not be null or empty");
            User existingUser = this.userService.findUserByUserNameFromDb(theater.getUserName());
            Assert.isTrue(existingUser != null,
                    "Invalid user name passed, no user found with id:  " + theater.getUserName());
            Assert.isTrue((existingUser.getUserType().toString().equals("ADMIN")) , "User is not allowed to add the theater, Only admin user can add a theater");

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public Threatre findThreatreFromName(String threatreId){
       return this.threatreRepository.getFromdb(threatreId);
    }

    public SimpleResponse updateThreatre(String threatreId, UpdateThreatreRequest request) {
        Threatre threatre = this.threatreRepository.getFromdb(threatreId);

        if(request.getName()!=null){
            threatre.setName(request.getName());
        }
        if(request.getLanguages()!=null){
            threatre.setLanguages(request.getLanguages());
        }
        this.threatreRepository.save(threatre);
        return this.simpleResponse.build(HttpStatus.OK.value(), "Threatre updated successfully");
    }

    public List<Threatre> getThreatreInACity(String city) {
        return this.threatreRepository.getThreatreInACity(city);
    }
}
