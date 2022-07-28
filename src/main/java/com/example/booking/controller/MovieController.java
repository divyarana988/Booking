package com.example.booking.controller;

import com.example.booking.entity.Movie;
import com.example.booking.exception.BasicException;
import com.example.booking.service.MovieService;
import com.example.booking.util.SimpleResponse;
import io.netty.handler.codec.serialization.ObjectEncoder;
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
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private SimpleResponse simpleResponse;

    @PostMapping(value = "/addMovie")
    public ResponseEntity<Object> addMovie(@RequestBody Movie movie) throws Exception {
        return this.movieService.addMovie(movie);
    }

    @GetMapping(value = "/getMoviesByCity")
    public ResponseEntity<Object> getMoviesByCity(@RequestParam("City") String city) throws Exception {
            List<Movie> availableMovies = this.movieService.getAvailableMovies(city);
            if(availableMovies.isEmpty()){
                return new ResponseEntity<>(this.simpleResponse.build(HttpStatus.BAD_REQUEST.value(), "No Movie found in this city"), HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<>(availableMovies, HttpStatus.OK);
            }
    }

    @GetMapping(value = "/getAllMoviesInAThreatre")
    public ResponseEntity<Object> getAllMoviesInAThreatre(@RequestParam("ThreatreId") String ThreatreId){
        return this.movieService.getAllMoviesInAThreatre(ThreatreId);
    }

}
