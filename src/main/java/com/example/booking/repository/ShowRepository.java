package com.example.booking.repository;

import com.example.booking.entity.ShowTiming;
import com.example.booking.entity.ShowHelper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowRepository extends JpaRepository<ShowTiming, ShowHelper> {

    @Query(value = "select * from showTiming where threatre_id = :threatreId and movie_id = :movieId", nativeQuery = true)
    List<ShowTiming> findShowByTheaterIdAndMovieId(@Param("threatreId") String threatreId, @Param("movieId") String movieId);

    @Query(value = "select * from showTiming where threatre_id = :threatreId and movie_id = :movieId and starts_at= :startsAt", nativeQuery = true )
    Optional<ShowTiming> findSameShow(@Param("threatreId") String threatreId, @Param("movieId") String movieId, @Param("startsAt") String startsAt);

    @Query(value = "select * from showTiming where threatre_id = :threatreId and movie_id = :movieId and starts_at= :startsAt", nativeQuery = true )
    ShowTiming findFromDb(@Param("threatreId") String threatreId, @Param("movieId") String movieId, @Param("startsAt") String startsAt);

}
