package com.example.booking.service;

import com.example.booking.entity.Booking;
import com.example.booking.entity.Notification;
import com.example.booking.entity.User;
import com.example.booking.enums.NotificationType;
import com.example.booking.enums.Status;
import com.example.booking.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    private static final String DO_NOT_REPLY_DUMMY_BMS_COM = "doNotReply@dummy.bms.com";

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UrlService urlService;

    public NotificationService(NotificationRepository notificationRepository, UrlService urlService){
        this.notificationRepository = notificationRepository;
        this.urlService = urlService;
    }

    public Notification sendNotification(Booking booking, User user) {
        Notification tobeSendNotification = new Notification();
        tobeSendNotification.setBookingId(booking.getBookingId());
        tobeSendNotification.setStatus(Status.SUCCESS);
        tobeSendNotification.setReceiverEmail(user.getEmail());
        tobeSendNotification.setReceiverMobileNo(user.getMobileNumber());
        tobeSendNotification.setReceiverType(NotificationType.EMAIL);
        tobeSendNotification.setSenderEmail(DO_NOT_REPLY_DUMMY_BMS_COM);
        this.urlService.setTinyUrl(tobeSendNotification, booking, booking.getUserId());
        tobeSendNotification.setCreatedOn(LocalDateTime.now());
        tobeSendNotification.setSendTime(LocalDateTime.now());
        Notification sentNotification = this.notificationRepository.save(tobeSendNotification);
        return sentNotification;
    }


}

