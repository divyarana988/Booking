package com.example.booking.service;

import com.example.booking.entity.*;
import com.example.booking.enums.SeatType;
import com.example.booking.exception.BasicException;
import com.example.booking.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private UserAuxilaryService userAuxilaryService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ThreatreRepository threatreRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private NotificationService notificationService;



    private final int DEFAULT_SEAT_COUNT = 50;
    private final int DEFAULT_SEAT_PRICE = 100;

    public SeatService(SeatRepository seatRepository, BookingRepository bookingRepository, UserAuxilaryService userAuxilaryService, NotificationService notificationService, ThreatreRepository threatreRepository, MovieRepository movieRepository){
        this.seatRepository = seatRepository;
        this.bookingRepository = bookingRepository;
        this.userAuxilaryService = userAuxilaryService;
        this.notificationService = notificationService;
        this.threatreRepository = threatreRepository;
        this.movieRepository = movieRepository;
    }

    private void validateInput(List<Seat> seatMatrix) {
        for (Seat seat : seatMatrix) {
            try {
                Assert.notNull(seat, "seat object must not be null");
                Assert.hasLength(seat.getPrimaryKey().getMovieId(), "seat movie id  must not be null or empty");
                Assert.hasLength(seat.getPrimaryKey().getThreatreId(),
                        "seat threatre id  must not be null or empty");
                Assert.hasLength(seat.getPrimaryKey().getShowStartsAt(),
                        "seat screen starts at  must not be null or empty");
                Assert.hasLength(String.valueOf(seat.getPrice()), "seat price must not be null or empty");
                Assert.hasLength(seat.getPrimaryKey().getSeatNumber(),
                        "seat seat number must not be null or empty");
                Assert.hasLength(seat.getSeatType().toString(), "seat seat number   must not be null or empty");
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    private String makeSeatNumber(int i) {
        StringBuffer sb = new StringBuffer();
        Map<Integer, Character> map = new HashMap<>();
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            int key = ch - 'A';
            char value = ch;
            map.put(key, value);
        }
        char ch = map.get(i / 10);
        int index = i % 10;
        sb.append("" + ch + index);
        return sb.toString();
    }
    public Seat addDefaultSeatMatrix(Seat seat) throws Exception {
        try {
            List<Seat> list = new ArrayList<>();
            list.add(seat);
            validateInput(list);
            for (int i = 0; i < DEFAULT_SEAT_COUNT; i++) {
                Seat newMatrix = new Seat();
                newMatrix.setBooked(false);
                newMatrix.setCreatedOn(LocalDateTime.now());
                newMatrix.setPrice(DEFAULT_SEAT_PRICE);
                newMatrix.setSeatType(SeatType.NORMAL);

                SeatHelper matrixHelper = new SeatHelper();
                matrixHelper.setMovieId(seat.getPrimaryKey().getMovieId());
                matrixHelper.setShowStartsAt(seat.getPrimaryKey().getShowStartsAt());
                matrixHelper.setThreatreId(seat.getPrimaryKey().getThreatreId());
                matrixHelper.setSeatNumber(makeSeatNumber(i));
                newMatrix.setPrimaryKey(matrixHelper);
                this.seatRepository.save(newMatrix);
            }

            return seat;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<Seat> getSeatMatrix(String movieId, String theaterId, String screenStartsAt) {
        List<Seat> seatsMatrix = this.seatRepository.getSeatForShow(movieId, theaterId, screenStartsAt);
        return seatsMatrix;
    }

    public List<Seat> getAvailabilityOnAScreen(String movieId, String theaterId, String screenStartsAt){
        List<Seat> seatsMatrix = this.getSeatMatrix(movieId, theaterId, screenStartsAt);
        return seatsMatrix;
    }

    /**
     * This service method will be responsible for below things: 1. Check if the
     * seats are already booked, if no then book these seats for this user in this
     * theater for this movie 2. do the payment , dummy payment details 3. if
     * payment is success then send the notification related to tickets 4. if
     * payment not success then mark booked seats as unbook , we can also book the
     * seats only if the payment is success but since there is a dummy payment data
     * here so I am just booking seats irrespective of payment status 5. return the
     * booking details to user 6. I am generating a tiny URL and that URL to user so
     * that user can retrieve the tickets
     *
     * Instead of making whole method synchronized , I have made logic of method
     * synchronized to improve performance Why block of this method synchronized: so
     * that not more than 2 threads can access it same time. But for now whole logic
     * is inside synchronized block
     *
     * @param seatsToBook
     * @param userName
     * @return
     * @throws JsonProcessingException
     * @throws JSONException
     */
    public JSONObject bookSeats(List<Seat> seatsToBook, String userName)
            throws JSONException, JsonProcessingException {
        StringBuilder sb = new StringBuilder();
        synchronized (seatsToBook) {
            int totalPrice = 0;
            // update seat matrix first
            for (Seat seat : seatsToBook) {
                Optional<Seat> seatFromDb = this.seatRepository.findById(seat.getPrimaryKey());
                Seat oSeatMatrix = seatFromDb.get();
                if (oSeatMatrix != null && oSeatMatrix.isBooked()) {
                    throw new BasicException(
                            "The seat number: " + seat.getPrimaryKey().getSeatNumber() + " is already booked");
                }
                oSeatMatrix.setBooked(true);
                totalPrice += oSeatMatrix.getPrice();
                this.seatRepository.save(oSeatMatrix);
                sb.append(seat.getPrimaryKey().getSeatNumber() + " ,");
            }
            String seatNumbers = sb.toString();
            seatNumbers = (seatNumbers.endsWith(",")) ? seatNumbers.substring(0, seatNumbers.length() - 2)
                    : seatNumbers;

            Seat seat = seatsToBook.get(0);
            Booking booking = new Booking();
            booking.setCreatedOn(LocalDateTime.now());
            booking.setUserId(userName);
            booking.setMovieId(seat.getPrimaryKey().getMovieId());
            booking.setThreatreId(seat.getPrimaryKey().getThreatreId());
            booking.setSeatBooked(seatsToBook.size());
            booking.setSeatNumbers(seatNumbers);
            booking.setTotalPrice(totalPrice);
            this.bookingRepository.save(booking);
            Payment dummyPayment = this.paymentService.doPayment(booking);
            // update payment id in booking
            booking.setPaymentId(dummyPayment.getId());
            this.bookingRepository.save(booking);

            // send the notification and update the notification id in booking table
            User user = this.userAuxilaryService.findUserByUserName(booking.getUserId());
            if (user == null)
                throw new BasicException("There is no user exists with user name: " + booking.getUserId());

            Notification sentNotification = this.notificationService.sendNotification(booking, user);
            booking.setNotificationId(sentNotification.getId());
            this.bookingRepository.save(booking);
            return makeReturnedData(booking, user);
        }
    }

    private JSONObject makeReturnedData(Booking booking, User user) throws JSONException {
        JSONObject result = new JSONObject();
        /**
         * user details:
         */
        JSONObject userDetails = new JSONObject();
        userDetails.put("bookedBy", user.getFirstName() + " " + user.getLastName());
        result.put("userDtails", userDetails);

        JSONObject movieDetails = new JSONObject();
        Optional<Movie> movie = this.movieRepository.findById(booking.getMovieId());
        Movie movieFromDb = movie.get();
        if (movieFromDb == null)
            throw new BasicException("There is no movie found with id  " + booking.getMovieId());
        movieDetails.put("movieName", movieFromDb.getName());
        result.put("movieDetails", movieDetails);

        JSONObject theaterDetails = new JSONObject();
        Optional<Threatre> theater = this.threatreRepository.findById(booking.getThreatreId());
        Threatre theaterFromDb = theater.get();
        if (theaterFromDb == null)
            throw new BasicException("There is no theater found with id  " + booking.getThreatreId());
        theaterDetails.put("theaterName", theaterFromDb.getName());
        theaterDetails.put("theaterAddress", theaterFromDb.getAddress());
        result.put("theaterDetails", theaterDetails);

        JSONObject ticketDetails = new JSONObject();
        ticketDetails.put("ticketNumbers", booking.getSeatNumbers());
        ticketDetails.put("totalPrice", booking.getTotalPrice());
        result.put("ticketDetails", ticketDetails);
        return result;
    }

}
