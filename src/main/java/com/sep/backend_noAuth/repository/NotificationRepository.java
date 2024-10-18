package com.sep.backend_noAuth.repository;

import com.sep.backend_noAuth.entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends MongoRepository<Notification,String> {
    List<Notification> findByCustomerId(String customerId);
    List<Notification> findByCustomerIdAndRead(String customerId, boolean read);
    List<Notification> findByCustomerIdAndType(String customerId, String type);
    Optional<Notification> findByNotificationId(String notificationId);
}
