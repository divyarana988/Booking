package com.example.booking.repository;

import com.example.booking.entity.Threatre;
import com.example.booking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ThreatreRepository extends JpaRepository<Threatre, String> {

    @Query(value = "select distinct(city) from threatre", nativeQuery = true)
    List<String> getSupportedCities();

    @Query(value = "select * from threatre where threatre_id = :threatreId and city = :city", nativeQuery = true)
    List<Threatre> getShowsShowingMovie(@Param("threatreId")String threatreId, @Param("city")String city);

    @Query(value = "select * from threatre where threatre_id = :threatreId", nativeQuery = true)
    Threatre getFromdb(@Param("threatreId") String threatreId);

    @Query(value = "select * from threatre where city= :city", nativeQuery = true)
    List<Threatre> getThreatreInACity(@Param("city") String city);

}
