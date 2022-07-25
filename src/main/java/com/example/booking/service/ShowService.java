package com.example.booking.service;

import com.example.booking.entity.Movie;
import com.example.booking.entity.Show;
import com.example.booking.entity.Threatre;
import com.example.booking.repository.MovieRepository;
import com.example.booking.repository.ShowRepository;
import com.example.booking.repository.ThreatreRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ThreatreRepository threatreRepository;

    @Autowired
    private MovieRepository movieRepository;


    public ShowService(ShowRepository showRepository, ThreatreRepository threatreRepository, MovieRepository movieRepository ){
        this.showRepository = showRepository;
        this.threatreRepository = threatreRepository;
        this.movieRepository = movieRepository;
    }

    public Show registerScreen(Show show) throws Exception {
        try {
            validateInput(show);
            this.showRepository.save(show);
            return show;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public JSONObject getScreensShowingMovie(String theaterId, String movieId, String city)
            throws JsonProcessingException, JSONException {
        List<Threatre> theatersShowingThisMovie = this.threatreRepository.getShowsShowingMovie(theaterId, city);
        JSONObject result = new JSONObject();
        for (Threatre threatre : theatersShowingThisMovie) {
            List<Show> shows = this.showRepository.findShowByTheaterIdAndMovieId(threatre.getThreatreId(), movieId);
            JSONArray screensArray = new JSONArray();
            for (Show screen : shows) {
                ObjectMapper mapper = new ObjectMapper();
                String screenJsonObj = mapper.writeValueAsString(screen);
                JSONObject obj = new JSONObject(screenJsonObj);
                screensArray.put(obj);
            }
            result.put(movieId, screensArray);
        }
        return result;

    }



    private void validateInput(Show show) {
        try {
            Assert.notNull(show, "show object must not be null");
            Assert.hasLength(show.getEndsAt().toString(), "show ends at  must not be null or empty");
            Assert.hasLength(show.getShowDetails().getMovieId().toString(), "show must have a movie id");
            Assert.hasLength(show.getShowDetails().getThreatreId().toString(), "show must have a theater id");
            Assert.hasLength(show.getShowDetails().getStartsAt().toString(), "show must have a starts at time ");
            Optional<Threatre> existingTheater = this.threatreRepository.findById(show.getShowDetails().getThreatreId());
            Assert.isTrue(existingTheater.get() != null, "Invalid threatre id passed, no threatre found with this id ");
            Optional<Movie> existingMovie = this.movieRepository.findById(show.getShowDetails().getMovieId());
            Assert.isTrue(existingMovie.get() != null, "Invalid movie id passed, no movie found with this id ");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

}
