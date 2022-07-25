package com.example.booking.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ShowHelper implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "threatre_id", nullable = false)
    private String threatreId;

    @Column(name = "movie_id", nullable = false)
    private String movieId;

    @Column(name = "starts_at", nullable = false)
    private String startsAt;

    public String getThreatreId() {
        return threatreId;
    }

    public void setThreatreId(String threatreId) {
        this.threatreId = threatreId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(String startsAt) {
        this.startsAt = startsAt;
    }


}
