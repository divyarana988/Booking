package com.example.booking.controller;

import com.example.booking.entity.Seat;
import com.example.booking.service.SeatService;
import com.example.booking.util.SimpleResponse;
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
    private SimpleResponse simpleResponse;

    @PostMapping(value = "/addDefaultSeat")
    public Seat addDefaultSeat(@RequestBody Seat seat) throws Exception {
        return this.seatService.addDefaultSeat(seat);
    }

    @GetMapping(value = "/getAvailabilityOnAShow")
    public List<Seat> getAvailabilityOnAShow(@RequestParam("movieId") String movieId,
                                                         @RequestParam("threatreId") String threatreId, @RequestParam("showStartsAt") String showStartsAt) throws JsonProcessingException {
        return this.seatService.getAvailabilityOnAShow(movieId, threatreId, showStartsAt);
    }

    @PostMapping(value = "/bookSeats")
    public ResponseEntity<Object> bookSeats(@RequestBody List<Seat> seatsToBook,
                                            @RequestParam("userName") String userName) {
        try {
            JSONObject bookingDetails = this.seatService.bookSeats(seatsToBook, userName);
            return new ResponseEntity<>(bookingDetails.toString(), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(this.simpleResponse.build(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(this.simpleResponse.build(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                  ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/cancelSeat")
    public SimpleResponse cancelSeat(@RequestParam("seatNumber") String seatNumber, @RequestParam("movieId") String movieId,
                                     @RequestParam("threatreId") String threatreId, @RequestParam("showStartsAt") String showStartsAt, @RequestParam("userName") String userName){
        return this.seatService.cancelSeat(seatNumber, movieId, threatreId, showStartsAt, userName);
    }


}

