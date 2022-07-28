package com.example.booking.controller;

import com.example.booking.Request.UpdateThreatreRequest;
import com.example.booking.entity.Threatre;
import com.example.booking.exception.ThreatreNotFoundException;
import com.example.booking.service.ThreatreAuxilaryService;
import com.example.booking.service.ThreatreService;
import com.example.booking.util.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.beans.SimpleBeanInfo;
import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@Component
@Configuration
public class ThreatreController {

    @Autowired
    private ThreatreAuxilaryService threatreAuxilaryService;

    @PostMapping(value = "/addThreatre")
    public SimpleResponse addThreatre(@RequestBody Threatre Threatre) throws IllegalArgumentException {
        return this.threatreAuxilaryService.addThreatre(Threatre);
    }

    @GetMapping(value = "/getSupportedCities")
    public List<String> getSupportedCities() throws ThreatreNotFoundException {
        return this.threatreAuxilaryService.getSupportedCities();
    }

    @GetMapping(value = "/getThreatreInACity")
    public ResponseEntity<Object> getThreatreInACity(@RequestParam("city") String city){
        return this.threatreAuxilaryService.getThreatreInACity(city);
    }

    @PutMapping(value = "/updateThreatre")
    public SimpleResponse updateThreatre(@RequestParam("threatreId") String threatreId, @RequestParam("userName") String userName, @RequestBody UpdateThreatreRequest request){
        return this.threatreAuxilaryService.updateThreatre(threatreId, userName,request);
    }

}
