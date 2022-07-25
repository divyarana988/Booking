package com.example.booking.controller;

import com.example.booking.entity.Movie;
import com.example.booking.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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

    @PostMapping(value = "/registerMovie")
    public Movie registerMovie(@RequestBody Movie movie) throws Exception {
        return this.movieService.registerMovie(movie);
    }

    @GetMapping(value = "/getMoviesByCity")
    public List<Movie> getMoviesByCity(@RequestParam("City") String city) throws Exception {
        try {
            List<Movie> availableMovies = this.movieService.getAvailableMovies(city);
            return availableMovies;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

}
