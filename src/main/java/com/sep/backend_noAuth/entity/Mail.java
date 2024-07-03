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

@Document(collection = "Mail")
public class Mail {

    @Transient
    public static final String SEQUENCE_NAME = "mail_sequence";

    @Id
    private String mailId;
    private String mailType;
    private String customerId;
    private String status;
    private String destinationAddress;
    private String recipientName;
    private String datePosted;
    private String dateDelivered;
}
