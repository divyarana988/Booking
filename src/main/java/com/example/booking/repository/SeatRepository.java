package com.example.booking.repository;

import com.example.booking.entity.Seat;
import com.example.booking.entity.SeatHelper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, SeatHelper> {

    @Query(value = "select * from seat where movie_id = :movieId and threatre_id=:threatreId and show_starts_at = :showStartsAt and booked = false ", nativeQuery = true)
    List<Seat> getSeatForShow(@Param("movieId")String movieId, @Param("threatreId")String threatreId, @Param
            ("showStartsAt") String showStartsAt);

}