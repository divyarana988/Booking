package com.example.booking.service;

import com.example.booking.entity.Movie;
import com.example.booking.entity.Threatre;
import com.example.booking.exception.BasicException;
import com.example.booking.repository.MovieRepository;
import com.example.booking.repository.ThreatreRepository;
import com.example.booking.util.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService{

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ThreatreRepository threatreRepository;

    @Autowired
    private SimpleResponse simpleResponse;


    public MovieService(MovieRepository movieRepository, ThreatreRepository threatreRepository, SimpleResponse simpleResponse){
        this.movieRepository = movieRepository;
        this.threatreRepository = threatreRepository;
        this.simpleResponse = simpleResponse;
    }

    public ResponseEntity<Object> addMovie(Movie movie) throws Exception {
        try {
            validateInput(movie);
            if(ifMovieExistInThreatre(movie)){
                return new ResponseEntity<>(this.simpleResponse.build(HttpStatus.BAD_REQUEST.value(), "This movie already exist in this threatre"), HttpStatus.BAD_REQUEST);
            }else{
                Movie newMovie = movie;
                String movieId = newMovie.getName().replaceAll("\\s", "");
                newMovie.setMovieId(movieId);
                newMovie.setCreatedOn(LocalDateTime.now());
                this.movieRepository.save(newMovie);
                return new ResponseEntity<>(newMovie, HttpStatus.OK);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    public Boolean ifMovieExistInThreatre(Movie movie){
        String threatreId = movie.getThreatreId();
        String movieId = movie.getName().replaceAll(" ", "");

        Movie movieFromDb = this.movieRepository.getMovieInAThreatre(threatreId,movieId);
        if(movieFromDb == null){
            return false;
        }else{
            return true;
        }
    }

    private void validateInput(Movie movie) {
        try {
            Assert.notNull(movie, "User object must not be null");
            Assert.hasLength(movie.getName(), "movie name must not be null or empty");
            Assert.hasLength(movie.getDuration(), "Movie duration must not be null or empty");
            Assert.hasLength(movie.getReleaseYear(), "Movie duration must not be null or empty");
            Assert.hasLength(movie.getActiveDateEnd().toString(), "Movie activate date end  must not be null or empty");
            Assert.hasLength(movie.getActiveDateStart().toString(),
                    "Movie active date start must not be null or empty");
            Assert.hasLength(movie.getLanguage().toString(), "Movie language must not be null or empty");
            Assert.hasLength(movie.getType().toString(), "Movie type must not be null or empty");
            Assert.notNull(movie.getThreatreId(), "theater id must not be null");
            Optional<Threatre> existingTheater = this.threatreRepository.findById(movie.getThreatreId());
            Assert.isTrue(existingTheater.get() != null, "Invalid theater id passed, no theater found with this id ");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public List<Movie> getAvailableMovies(String city) {
        List<Movie> availableMovies = this.movieRepository.getAvailableMovies(city);
        return availableMovies;
    }

    public ResponseEntity<Object> getAllMoviesInAThreatre(String threatreId){
        if(this.threatreRepository.findById(threatreId).isEmpty()){
            return new ResponseEntity<>(this.simpleResponse.build(HttpStatus.BAD_REQUEST.value(), "Threatre does not exist"), HttpStatus.BAD_REQUEST);
        }else{
            List<Movie> movieList= this.movieRepository.getAllMoviesInAThreatre(threatreId);
            if(movieList.isEmpty()){
                return new ResponseEntity<>(this.simpleResponse.build(HttpStatus.BAD_REQUEST.value(), "No movie found in this threatre"), HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<>(movieList, HttpStatus.OK);
            }
        }
    }

}

