package com.example.booking.service;

import com.example.booking.Request.UpdateCastRequest;
import com.example.booking.entity.Cast;
import com.example.booking.entity.Movie;
import com.example.booking.repository.CastRepository;
import com.example.booking.repository.MovieRepository;
import com.example.booking.util.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CastService {

    @Autowired
    private CastRepository castRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SimpleResponse simpleResponse;

    public CastService(CastRepository castRepository, MovieRepository movieRepository, SimpleResponse simpleResponse){
        this.castRepository = castRepository;
        this.movieRepository = movieRepository;
        this.simpleResponse = simpleResponse;
    }

    public List<Cast> findByMovieId(String movieId){
        List<Cast> casts = this.castRepository.getCastByMovieId(movieId);
        return casts;
    }

    public List<Cast> addCast(List<Cast> casts) throws Exception {
        try {
            for (Cast cast : casts) {
                validateInput(cast);
                this.castRepository.save(cast);
                Optional<Movie> existingMovie = this.movieRepository.findById(cast.getMovieId());
                if(existingMovie.isPresent()){
                    Movie movie = existingMovie.get();
                    movie.setCastId(cast.getMovieId());
                    this.movieRepository.save(movie);
                }
            }
            return casts;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private void validateInput(Cast cast) {
        try {
            Assert.notNull(cast, "User object must not be null");
            Assert.hasLength(cast.getCastDetails(), "cast details  must not be null or empty");
            Assert.hasLength(cast.getCharacterName(), "Cast character must not be null or empty");
            Assert.hasLength(cast.getMovieId(), "Movie duration must not be null or empty");
            Optional<Movie> existingTheater = this.movieRepository.findById(cast.getMovieId());
            Assert.isTrue(existingTheater.get() != null, "Invalid movie id  passed, no movie found with this id ");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public List<Cast> getCast(String movieId) throws Exception {
        try {
            List<Cast> casts = this.findByMovieId(movieId);
            return casts;
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public SimpleResponse updateCast(String castId, UpdateCastRequest request) {
        Cast castFromDb = this.castRepository.findByCastId(castId);
        if(castFromDb == null){
            return this.simpleResponse.build(HttpStatus.BAD_REQUEST.value(), "Cast with this castId not found");
        }else{
            if(request.getCastDetails() != null){
                castFromDb.setCastDetails(request.getCastDetails());
            }
            if(request.getCharacterName() != null){
                castFromDb.setCharacterName(request.getCharacterName());
            }
            this.castRepository.save(castFromDb);
            return this.simpleResponse.build(HttpStatus.OK.value(), "Successfully updated cast details");
        }
    }
}