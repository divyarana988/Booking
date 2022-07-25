package com.example.booking.service;

import com.example.booking.entity.Threatre;
import com.example.booking.entity.User;
import com.example.booking.exception.ThreatreNotFoundException;
import com.example.booking.repository.ThreatreRepository;
import com.example.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ThreatreService {
    @Autowired
    private ThreatreRepository threatreRepository;
    @Autowired
    private UserAuxilaryService userAuxilaryService;

    @Autowired
    public ThreatreService(ThreatreRepository threatreRepository, UserAuxilaryService userAuxilaryService){
        this.threatreRepository = threatreRepository;
        this.userAuxilaryService = userAuxilaryService;
    }

    public Threatre registerTheater(Threatre theater) throws IllegalArgumentException {
        try {
            validateInput(theater);
            Threatre newTheater = theater;
            String theaterId = newTheater.getName().replaceAll("\\s", "");
            newTheater.setThreatreId(theaterId);
            newTheater.setCreatedOn(LocalDateTime.now());
            this.threatreRepository.save(newTheater);
            return newTheater;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
            // return e;
        }
    }

    public List<String> getSupportedCities() throws ThreatreNotFoundException {
            return this.getFromDb()
                    .orElseThrow(() -> new ThreatreNotFoundException("No city found"));
    }

    private Optional<List<String>> getFromDb() {
        return this.threatreRepository.getSupportedCities();
    }

    private void validateInput(Threatre theater) {
        try {
            Assert.notNull(theater, "User object must not be null");
            Assert.hasLength(theater.getName(), "theater name must not be null or empty");
            Assert.hasLength(theater.getAddress(), "theater address must not be null or empty");
            Assert.hasLength(theater.getCity(), "theater city must not be null or empty");
            Assert.hasLength(theater.getLanguages(), "theater language must not be null or empty");
            Assert.hasLength(theater.getUserName(), "theater user name must not be null or empty");
            User existingUser = this.userAuxilaryService.findUserByUserName(theater.getUserName());
            Assert.isTrue(existingUser != null,
                    "Invalid user name passed, no user found with id:  " + theater.getUserName());
            Assert.isTrue((existingUser.getUserType().toString().equals("ADMIN")) , "User is not allowed to add the theater, Only admin user can add a theater");

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

}
