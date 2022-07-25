package com.example.booking.controller;

import com.example.booking.entity.Cast;
import com.example.booking.service.CastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@Component
@Configuration
public class CastController {
    @Autowired
    private CastService castService;

    @PostMapping(value = "/addCast")
    public List<Cast> addCast(@RequestBody List<Cast> casts) throws Exception {
       return this.castService.addCast(casts);
    }

    @GetMapping(value = "/getCast")
    public List<Cast> getCast(@RequestParam("movieId") String movieId) throws Exception {
        return this.castService.getCast(movieId);
    }
}