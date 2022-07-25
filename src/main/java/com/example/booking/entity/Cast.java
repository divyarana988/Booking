package com.example.booking.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cast")
public class Cast implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "movie_id")
    private String movieId;

    @Column(name = "character_name")
    private String characterName;

    @Column(name = "description")
    private String castDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

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

    @Override
    public String toString() {
        return "Cast [id=" + id + ", movieId=" + movieId + ", characterName=" + characterName + ", castDetails=" + castDetails + "]";
    }

}
