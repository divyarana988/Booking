package com.example.booking.controller;

import com.example.booking.entity.Threatre;
import com.example.booking.exception.ThreatreNotFoundException;
import com.example.booking.service.ThreatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@Component
@Configuration
public class ThreatreController {

    @Autowired
    private ThreatreService threatreService;

    @PostMapping(value = "/addThreatre")
    public Threatre registerTheater(@RequestBody Threatre Threatre) throws IllegalArgumentException {
        return this.threatreService.registerTheater(Threatre);
    }

    @GetMapping(value = "/getSupportedCities")
    public List<String> getSupportedCities() throws ThreatreNotFoundException {
        return this.threatreService.getSupportedCities();
    }

}
