package com.sep.backend_noAuth.controller;

import com.sep.backend_noAuth.dto.NotificationDto;
import com.sep.backend_noAuth.entity.Notification;
import com.sep.backend_noAuth.entity.OfficeNotification.DeliveryManagerNotification;
import com.sep.backend_noAuth.repository.Notification.DeliveryManagerNotificationRepository;
import com.sep.backend_noAuth.repository.NotificationRepository;
import com.sep.backend_noAuth.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private DeliveryManagerNotificationRepository deliveryManagerNotificationRepository;

    @MessageMapping("/notify")
    @SendTo("/topic/notifications") // Default destination for messages
    public Notification sendBroadcastNotification(NotificationDto dto) {
        Notification notification = notificationService.createNotification(dto);
        notificationRepository.save(notification);
        return notification;
    }
    // This method now sends to a specific customer based on their ID
    @MessageMapping("/notify/{customerId}")
    @SendTo("/topic/customer/{customerId}") // Sending notification to a specific customer based on customerId
    public Notification sendNotification(NotificationDto dto, @PathVariable String customerId) {
        Notification notification = notificationService.createNotification(dto);
        notificationRepository.save(notification);
        return notification;
    }

    @GetMapping("/api/notifications/{customerId}")
    public List<Notification> getNotifications(@PathVariable String customerId) {
        return notificationRepository.findByCustomerId(customerId);
    }
    @GetMapping("/api/notifications/unread/{customerId}")
    public List<Notification> getUnreadNotifications(@PathVariable String customerId) {
        return notificationRepository.findByCustomerIdAndRead(customerId,false);
    }
    @GetMapping("/api/notifications/delivery-manager/today/{managerId}")
    public List<DeliveryManagerNotification> getDeliveryManagerNotification(@PathVariable String managerId){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        String date = currentDate.format(formatter);
        return deliveryManagerNotificationRepository.findByDateAndManagerId(date, managerId);
    }

//    @MessageMapping("/notify")
//    @SendTo("/topic/notifications") // Default destination for messages
//    public Notification sendNotification(Notification notification) {
//        notificationRepository.save(notification);
//        return notification;
//    }
//
//    @GetMapping("/api/notifications/{customerId}")
//    public List<Notification> getNotifications(@PathVariable String customerId) {
//        return notificationRepository.findByCustomerId(customerId);
//    }
}
