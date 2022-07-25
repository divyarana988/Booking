package com.example.booking.repository;

import com.example.booking.entity.Cast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CastRepository extends JpaRepository<Cast, Long> {
    @Query(value = "select * from cast where movie_id = :movieId", nativeQuery=true)
    List<Cast> getCastByMovieId(@Param("movieId") String movieId);

}