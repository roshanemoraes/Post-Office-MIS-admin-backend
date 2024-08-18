package com.sep.backend_noAuth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Document(collection = "Notification")
public class Notification {

    @Transient
    public static final String SEQUENCE_NAME = "notification_sequence";

    @Id
    private String notificationId;
    private String customerId;
    private String message;
    private String date;
    private boolean read;
}
