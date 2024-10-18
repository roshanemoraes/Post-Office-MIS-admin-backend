package com.sep.backend_noAuth.repository.Notification;

import com.sep.backend_noAuth.entity.OfficeNotification.DeliveryManagerNotification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeliveryManagerNotificationRepository extends MongoRepository<DeliveryManagerNotification, String> {
    List<DeliveryManagerNotification> findByDateAndManagerId(String date, String managerId);
}
