package com.example.booking.controller;

import com.example.booking.entity.Show;
import com.example.booking.service.ShowService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1")
@Component
@Configuration
public class ShowController {

    @Autowired
    private ShowService showService;


    @PostMapping(value = "/addShow")
    public Show registerScreen(@RequestBody Show show) throws Exception {
        return this.showService.registerScreen(show);
    }

    @GetMapping(value = "/getShowsShowingMovie")
    public ResponseEntity<Object> getScreensShowingMovie(@RequestParam("threatreId") String threatreId,
                                                         @RequestParam("movieId") String movieId, @RequestParam("city") String city) throws Exception {
        try {
            JSONObject screensShowingMovie = this.showService.getScreensShowingMovie(threatreId, movieId, city);
            return new ResponseEntity<>(screensShowingMovie.toString(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

}
