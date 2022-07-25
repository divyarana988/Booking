package com.example.booking.service;

import com.example.booking.entity.Movie;
import com.example.booking.entity.Threatre;
import com.example.booking.repository.MovieRepository;
import com.example.booking.repository.ThreatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
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


    public MovieService(MovieRepository movieRepository, ThreatreRepository threatreRepository){
        this.movieRepository = movieRepository;
        this.threatreRepository = threatreRepository;
    }

    public Movie registerMovie(Movie movie) throws Exception {
        try {
            validateInput(movie);
            Movie newMovie = movie;
            String movieId = newMovie.getName().replaceAll("\\s", "");
            newMovie.setMovieId(movieId);
            newMovie.setCreatedOn(LocalDateTime.now());
            this.movieRepository.save(newMovie);
            return newMovie;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
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

}

