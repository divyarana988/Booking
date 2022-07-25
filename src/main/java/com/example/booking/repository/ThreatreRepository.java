package com.example.booking.repository;

import com.example.booking.entity.Threatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ThreatreRepository extends JpaRepository<Threatre, String> {

    @Query(value = "select distinct(city) from threatre", nativeQuery = true)
    Optional<List<String>> getSupportedCities();

    @Query(value = "select * from theater where threatre_id = :threatreId and city = :city", nativeQuery = true)
    List<Threatre> getShowsShowingMovie(@Param("threatreId")String threatreId, @Param("city")String city);
}
