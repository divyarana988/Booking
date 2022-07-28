package com.example.booking.controller;

import com.example.booking.entity.ShowTiming;
import com.example.booking.service.ShowAuxilaryService;
import com.example.booking.service.ShowService;
import com.example.booking.util.SimpleResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@RequestMapping(value = "/v1")
@Component
@Configuration
public class ShowController {

    @Autowired
    private ShowAuxilaryService showAuxilaryService;

    @Autowired
    private SimpleResponse simpleResponse;


    @PostMapping(value = "/addShow")
    public SimpleResponse addShow(@RequestBody ShowTiming showTiming) throws Exception {
        return this.showAuxilaryService.addShow(showTiming);
    }

    @GetMapping(value = "/getShowsShowingMovie")
    public ResponseEntity<Object> getShowsShowingMovie(@RequestParam("threatreId") String threatreId,
                                                         @RequestParam("movieId") String movieId, @RequestParam("city") String city) throws Exception {

        try {
            JSONObject screensShowingMovie = this.showAuxilaryService.getShowsShowingMovie(threatreId, movieId, city);
            return new ResponseEntity<>(screensShowingMovie.toString(), HttpStatus.OK);
        }  catch (IllegalArgumentException e) {
            return new ResponseEntity<>(this.simpleResponse.build(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(this.simpleResponse.build(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/deleteAShow")
    public SimpleResponse deleteAShow(@RequestParam("threatreId") String threatreId, @RequestParam("movieId") String movieId, @RequestParam("startsAt")String startsAt, @RequestParam("userName") String userName){
        return this.showAuxilaryService.deleteAShow(threatreId, movieId, startsAt, userName);
    }

}
