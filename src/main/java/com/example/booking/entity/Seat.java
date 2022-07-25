package com.example.booking.entity;

import com.example.booking.enums.SeatType;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "seat")
public class Seat implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private SeatHelper primaryKey;


    @Column(name = "booked")
    private boolean booked;

    @Column(name = "seat_type")
    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @Column(name = "created_on")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn =  LocalDateTime.now();

    @Column(name = "seat_price")
    private int price;

    public SeatHelper getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(SeatHelper primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Seat [primaryKey=" + primaryKey + ", booked=" + booked + ", seatType=" + seatType + ", createdOn="
                + createdOn + ", price=" + price + "]";
    }

}

