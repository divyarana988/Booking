package com.example.booking.controller;

import com.example.booking.entity.Seat;
import com.example.booking.service.SeatService;
import com.example.booking.util.ResponseParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@Component
@Configuration
public class SeatController {
    @Autowired
    private SeatService seatService;

    @Autowired
    private ResponseParser responseParser;

    @PostMapping(value = "/addDefaultSeat")
    public Seat addDefaultSeatMatrix(@RequestBody Seat seatMatrix) throws Exception {
        return this.seatService.addDefaultSeatMatrix(seatMatrix);
    }

    @GetMapping(value = "/getAvailabilityOnAShow")
    public List<Seat> getAvailabilityOnAScreen(@RequestParam("movieId") String movieId,
                                                     @RequestParam("threatreId") String theaterId, @RequestParam("showStartsAt") String screenStartsAt) throws JsonProcessingException {
        return this.seatService.getAvailabilityOnAScreen(movieId, theaterId, screenStartsAt);
    }

    @PostMapping(value = "/bookSeats")
    public ResponseEntity<Object> bookSeats(@RequestBody List<Seat> seatsToBook,
                                            @RequestParam("userName") String userName) {
        try {
            JSONObject bookingDetails = this.seatService.bookSeats(seatsToBook, userName);
            return new ResponseEntity<>(bookingDetails.toString(), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(this.responseParser.build(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(this.responseParser.build(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    ex.getMessage(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

