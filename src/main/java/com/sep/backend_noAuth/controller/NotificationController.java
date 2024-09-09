package com.sep.backend_noAuth.controller;

import com.sep.backend_noAuth.dto.NotificationDto;
import com.sep.backend_noAuth.entity.Notification;
import com.sep.backend_noAuth.repository.NotificationRepository;
import com.sep.backend_noAuth.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationService notificationService;

    @MessageMapping("/notify")
    @SendTo("/topic/notifications") // Default destination for messages
    public Notification sendNotification(NotificationDto dto) {
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
