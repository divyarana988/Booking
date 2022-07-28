package com.example.booking.service;

import com.example.booking.entity.ShowTiming;
import com.example.booking.enums.UserType;
import com.example.booking.util.SimpleResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowAuxilaryService {
    @Autowired
    private ShowService showService;

    @Autowired
    private SimpleResponse simpleResponse;

    @Autowired
    private UserService userService;

    public ShowAuxilaryService(ShowService showService, SimpleResponse simpleResponse, UserService userService){
        this.showService = showService;
        this.simpleResponse = simpleResponse;
        this.userService = userService;
    }

    public SimpleResponse addShow(ShowTiming showTiming) throws Exception {
        Optional<ShowTiming> showFromDb = this.showService.findSameShow(showTiming.getShowDetails().getThreatreId(), showTiming.getShowDetails().getMovieId(), showTiming.getShowDetails().getStartsAt());
        if(showFromDb.isPresent()){
            return this.simpleResponse.build(HttpStatus.BAD_REQUEST.value(), "Show already exist");
        }else{
            return this.showService.addShow(showTiming);
        }
    }

    public JSONObject getShowsShowingMovie(String threatreId, String movieId, String city) throws JsonProcessingException {
        JSONObject res = this.showService.getShowsShowingMovie(threatreId , movieId, city);
        if(res.isEmpty()){
            return new JSONObject("{Error message : No such show exist}");
        }else{
            return res;
        }
    }

    public SimpleResponse deleteAShow(String threatreId, String movieId, String startsAt, String userName) {
        if(this.userService.findUserByUserNameFromDb(userName) == null){
            return this.simpleResponse.build(HttpStatus.BAD_REQUEST.value(), "User does not exist");
        }
        else if(this.userService.findUserByUserNameFromDb(userName).getUserType() != UserType.ADMIN){
            return this.simpleResponse.build(HttpStatus.BAD_REQUEST.value(), "User is not allowed to delete the show, Only admin user can delete a show");
        }else{
            return this.showService.deleteAShow(threatreId, movieId, startsAt, userName);
        }
    }
}
