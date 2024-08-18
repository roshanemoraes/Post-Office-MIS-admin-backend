package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.dto.NotificationDto;
import com.sep.backend_noAuth.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class NotificationService {

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    public Notification createNotification(NotificationDto dto){
        Notification notification = new Notification();
        notification.setNotificationId(String.valueOf(sequenceGeneratorService.getSequenceNumber(Notification.SEQUENCE_NAME)));

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

        notification.setDate(currentDate.format(formatter));
        notification.setRead(false);
        notification.setMessage(dto.getMessage());
        notification.setCustomerId(dto.getCustomerId());
        return notification;
    }
}
