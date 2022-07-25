package com.example.booking.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Threatre")
public class Threatre implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "threatre_id")
    private String threatreId;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "name")
    private String name;

    @Column(name = "languages")
    private String languages;

    @Column(name = "created_on")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn = LocalDateTime.now() ;


    @Column(name = "user_name")
    private String userName;

    public Threatre() {};

    public Threatre(String threatreId, String address, String city, String name, String languages, String userName) {
        super();
        this.threatreId = threatreId;
        this.address = address;
        this.city = city;
        this.name = name;
        this.languages = languages;
        this.userName = userName;
    }

    public String getThreatreId() {
        return threatreId;
    }

    public void setThreatreId(String threatreId) {
        this.threatreId = threatreId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Threatre [threatreId=" + threatreId + ", address=" + address + ", city=" + city
                + ", name=" + name + ", languages=" + languages + ", createdOn=" + createdOn + ", userName=" + userName + "]";
    }

}

