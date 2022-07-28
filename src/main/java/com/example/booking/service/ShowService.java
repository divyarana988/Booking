package com.example.booking.service;

import com.example.booking.entity.Movie;
import com.example.booking.entity.ShowTiming;
import com.example.booking.entity.Threatre;
import com.example.booking.entity.User;
import com.example.booking.repository.MovieRepository;
import com.example.booking.repository.ShowRepository;
import com.example.booking.repository.ThreatreRepository;
import com.example.booking.repository.UserRepository;
import com.example.booking.util.SimpleResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.beans.SimpleBeanInfo;
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

    @Autowired
    private SimpleResponse simpleResponse;

    @Autowired
    private UserService userService;


    public ShowService(ShowRepository showRepository, ThreatreRepository threatreRepository, MovieRepository movieRepository, SimpleResponse simpleResponse,  UserService userService ){
        this.showRepository = showRepository;
        this.threatreRepository = threatreRepository;
        this.movieRepository = movieRepository;
        this.simpleResponse = simpleResponse;
        this.userService = userService;
    }

    public SimpleResponse addShow(ShowTiming showTiming) throws Exception {
        try {
            validateInput(showTiming);
            this.showRepository.save(showTiming);
            return this.simpleResponse.build(HttpStatus.OK.value(), "Show added successfully");
        } catch (Exception e) {
            return this.simpleResponse.build(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    public JSONObject getShowsShowingMovie(String theaterId, String movieId, String city)
            throws JsonProcessingException, JSONException {
        List<Threatre> theatersShowingThisMovie = this.threatreRepository.getShowsShowingMovie(theaterId, city);
        JSONObject result = new JSONObject();
        for (Threatre threatre : theatersShowingThisMovie) {
            List<ShowTiming> showTimings = this.showRepository.findShowByTheaterIdAndMovieId(threatre.getThreatreId(), movieId);
            JSONArray screensArray = new JSONArray();
            for (ShowTiming screen : showTimings) {
                ObjectMapper mapper = new ObjectMapper();
                String screenJsonObj = mapper.writeValueAsString(screen);
                JSONObject obj = new JSONObject(screenJsonObj);
                screensArray.put(obj);
            }
            result.put(movieId, screensArray);
        }
        return result;

    }

    public List<ShowTiming> findShowByTheaterIdAndMovieId(String threatreId, String movieId){
        return this.showRepository.findShowByTheaterIdAndMovieId(threatreId, movieId);
    }

    private void validateInput(ShowTiming showTiming) {
        try {
            Assert.notNull(showTiming, "show object must not be null");
            Assert.hasLength(showTiming.getEndsAt().toString(), "show ends at  must not be null or empty");
            Assert.hasLength(showTiming.getShowDetails().getMovieId().toString(), "show must have a movie id");
            Assert.hasLength(showTiming.getShowDetails().getThreatreId().toString(), "show must have a theater id");
            Assert.hasLength(showTiming.getShowDetails().getStartsAt().toString(), "show must have a starts at time ");
            Optional<Threatre> existingTheater = this.threatreRepository.findById(showTiming.getShowDetails().getThreatreId());
            Assert.isTrue(existingTheater.get() != null, "Invalid threatre id passed, no threatre found with this id ");
            Optional<Movie> existingMovie = this.movieRepository.findById(showTiming.getShowDetails().getMovieId());

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public Optional<ShowTiming> findSameShow(String threatreId, String movieId, String startsAt){
        return this.showRepository.findSameShow(threatreId, movieId, startsAt);
    }

    public SimpleResponse deleteAShow(String threatreId, String movieId, String startsAt, String userName) {
        try{
            ShowTiming show = this.showRepository.findFromDb(threatreId, movieId, startsAt);
            if(show == null){
                return this.simpleResponse.build(HttpStatus.BAD_REQUEST.value(), "Show does not exist");
            }else{
                ShowTiming showFromDb = this.showRepository.findFromDb(threatreId, movieId, startsAt);
                this.showRepository.delete(showFromDb);
                return this.simpleResponse.build(HttpStatus.OK.value(), "Successfully deleted this show");
            }
        }
        catch(Exception e){
            return this.simpleResponse.build(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
}
