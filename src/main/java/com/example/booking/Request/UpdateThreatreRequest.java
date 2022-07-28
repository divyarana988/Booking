package com.example.booking.Request;

import org.springframework.stereotype.Component;

import javax.persistence.Column;

@Component
public class UpdateThreatreRequest {

    private String name;

    private String languages;

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
}
