package com.example.booking.service;

import com.example.booking.Request.UpdateThreatreRequest;
import com.example.booking.entity.Threatre;
import com.example.booking.entity.User;
import com.example.booking.enums.UserType;
import com.example.booking.exception.BasicException;
import com.example.booking.util.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThreatreAuxilaryService {

    @Autowired
    private ThreatreService threatreService;

    @Autowired
    private SimpleResponse simpleResponse;

    @Autowired
    private UserService userService;

    public ThreatreAuxilaryService(ThreatreService threatreService, SimpleResponse simpleResponse, UserService userService){
        this.threatreService = threatreService;
        this.simpleResponse = simpleResponse;
        this.userService = userService;
    }


    public SimpleResponse addThreatre(Threatre threatre) {
        Threatre threatreFromDb = this.threatreService.findThreatreFromName(threatre.getName().replaceAll(" ", ""));

        if(threatreFromDb != null && threatreFromDb.getAddress().equals(threatre.getAddress())  && threatreFromDb.getCity().equals(threatre.getCity())){
            return this.simpleResponse.build(HttpStatus.BAD_REQUEST.value(), "Threatre already exist");
        }else{
            return this.threatreService.addThreatre(threatre);
        }

    }

    public List<String> getSupportedCities() {
        return this.threatreService.getSupportedCities();
    }

    public SimpleResponse updateThreatre(String threatreId, String userName, UpdateThreatreRequest request) {
        User user = this.userService.findUserByUserNameFromDb(userName);
        if(user.getUserType()== UserType.ADMIN){
            Threatre threatreFromDb = this.threatreService.findThreatreFromName(threatreId);
            if(threatreFromDb == null){
                return this.simpleResponse.build(HttpStatus.BAD_REQUEST.value(), "Threatre with this threatreId not found");
            }else{
                return this.threatreService.updateThreatre(threatreId, request);
            }
        }else{
            return this.simpleResponse.build(HttpStatus.BAD_REQUEST.value(), "Only admin can update threatre info");
        }

    }

    public ResponseEntity<Object> getThreatreInACity(String city) throws BasicException{
        List<Threatre> fromDb =  this.threatreService.getThreatreInACity(city);
        if(!fromDb.isEmpty()){
             return new ResponseEntity<>(fromDb, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(this.simpleResponse.build(HttpStatus.BAD_REQUEST.value(), "No Threatre exist in this city"), HttpStatus.BAD_REQUEST);
        }
    }
}
