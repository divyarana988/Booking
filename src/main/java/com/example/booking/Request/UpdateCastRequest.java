package com.example.booking.Request;

import org.springframework.stereotype.Component;

import javax.persistence.Column;

@Component
public class UpdateCastRequest {

    private String characterName;

    private String castDetails;

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getCastDetails(){
        return castDetails;
    }

    public void setCastDetails(String castDetails) {
        this.castDetails = castDetails;
    }
}
