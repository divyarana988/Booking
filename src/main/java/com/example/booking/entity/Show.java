package com.example.booking.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "show")
public class Show implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private ShowHelper showDetails;

    @Column(name = "ends_at")
    private String endsAt;

    public ShowHelper getShowDetails() {
        return showDetails;
    }

    public void setShowDetails(ShowHelper showDetails) {
        this.showDetails = showDetails;
    }

    public String getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(String endsAt) {
        this.endsAt = endsAt;
    }

    @Override
    public String toString() {
        return "Show [showDetails=" + showDetails + ", endsAt=" + endsAt + "]";
    }
}

