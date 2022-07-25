package com.example.booking.service;

import com.example.booking.entity.Booking;
import com.example.booking.entity.Payment;
import com.example.booking.enums.PaymentMethod;
import com.example.booking.enums.Status;
import com.example.booking.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }

    public Payment doPayment(Booking booking) {
        Payment dummyPayment = new Payment();
        dummyPayment.setBookingId(booking.getBookingId());
        dummyPayment.setAmount(booking.getTotalPrice());
        dummyPayment.setStatus(Status.SUCCESS);
        dummyPayment.setCreatedOn(LocalDateTime.now());
        dummyPayment.setMethod(PaymentMethod.CREDITCARD);
        dummyPayment.setSourceDetails("HDFC CC NO: 123456789123");
        /**
         * TODO : if payment is not success then un-book the seats
         */

        Payment paymentFromDb = this.paymentRepository.save(dummyPayment);
        return paymentFromDb;
    }

}
