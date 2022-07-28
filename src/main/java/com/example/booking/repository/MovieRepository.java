package com.example.booking.repository;

import com.example.booking.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String>
{

    @Query(value = "select * from movie where threatre_id = :threatreId", nativeQuery = true)
    List<Movie> getAllMoviesInAThreatre(@Param("threatreId") String threatreId);


    @Query(value = "select m.* from movie m join threatre t on  t.threatre_id = m.threatre_id and t.city  = :city",  nativeQuery = true)
    List<Movie> getAvailableMovies(@Param("city")String city);

    @Query(value = "select * from movie where `name` = :name and threatre_id=:threatreId", nativeQuery = true)
    Movie getMovieInAThreatre(@Param("name") String name, @Param("threatreId")String threatreId);

}