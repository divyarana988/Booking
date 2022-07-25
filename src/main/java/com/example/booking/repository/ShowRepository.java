package com.example.booking.repository;

import com.example.booking.entity.Show;
import com.example.booking.entity.ShowHelper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, ShowHelper> {

    @Query(value = " select * from show where threatre_id=:threatreId and movie_id = :movieId", nativeQuery = true)
    List<Show> findShowByTheaterIdAndMovieId(@Param("threatreId") String threatreId, @Param("movieId") String movieId);

}
