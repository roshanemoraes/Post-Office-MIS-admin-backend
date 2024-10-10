package com.sep.backend_noAuth.entity.OfficeNotification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Notification-Delivery-Manager" )
public class DeliveryManagerNotification {
    @Id
    String notificationId;
    String managerId;
    String message;
    String date;
    String type;
}
