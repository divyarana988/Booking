package com.example.booking.entity;

import com.example.booking.enums.Language;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "movie")
public class Movie implements Serializable {

    private static final long serialVersionUID = -2343243243242432341L;


    @Id
    private String movieId;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "language")
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name = "threatre_id")
    private String threatreId;

    @Column(name = "rating")
    private String rating;

    @Column(name = "cast_id")
    private String castId;

    @Column(name = "release_year")
    private String releaseYear;

    @Column(name = "active_date_start")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activeDateStart;

    @Column(name = "active_date_end")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activeDateEnd;


    @Column(name = "duration")
    private String duration;


    @Column(name = "created_on")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn = LocalDateTime.now();

    @Column(name = "director")
    private String director;


    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getThreatreId() {
        return threatreId;
    }

    public void setThreatreId(String threatreId) {
        this.threatreId = threatreId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCastId() {
        return castId;
    }

    public void setCastId(String castId) {
        this.castId = castId;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public LocalDateTime getActiveDateStart() {
        return activeDateStart;
    }

    public void setActiveDateStart(LocalDateTime activeDateStart) {
        this.activeDateStart = activeDateStart;
    }

    public LocalDateTime getActiveDateEnd() {
        return activeDateEnd;
    }

    public void setActiveDateEnd(LocalDateTime activeDateEnd) {
        this.activeDateEnd = activeDateEnd;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }


    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }


    @Override
    public String toString() {
        return "Movie [movieId=" + movieId + ", name=" + name + ", type=" + type + ", language=" + language
                + ", threatreId=" + threatreId + ", rating=" + rating + ", castId=" + castId + ", releaseYear="
                + releaseYear + ", activeDateStart=" + activeDateStart + ", activeDateEnd=" + activeDateEnd
                + ", duration=" + duration + ", createdOn="
                + createdOn + ", director=" + director + "]";
    }


}
