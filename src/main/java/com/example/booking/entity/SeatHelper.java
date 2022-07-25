package com.example.booking.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class SeatHelper implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "movie_id")
    private String movieId;

    @Column(name = "threatre_id")
    private String threatreId;

    @Column(name = "show_starts_at")
    private String showStartsAt;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getThreatreId() {
        return threatreId;
    }

    public void setThreatreId(String threatreId) {
        this.threatreId = threatreId;
    }

    public String getShowStartsAt() {
        return showStartsAt;
    }

    public void setShowStartsAt(String showStartsAt) {
        this.showStartsAt = showStartsAt;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Override
    public String toString() {
        return "SeatHelper [movieId=" + movieId + ", threatreId=" + threatreId + ", showStartsAt=" + showStartsAt
                + ", seatNumber=" + seatNumber + "]";
    }

}

