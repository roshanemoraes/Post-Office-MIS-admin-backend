package com.sep.backend_noAuth.dto.PostTypes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NormalCourierPostDto {
    private String recipientId;
    private String recipientName;
    private String recipientHouseNumber;
    private String recipientPostalZone;
    private String recipientCity;
    private String recipientAddress;
    private Double postage;
    private String courierProvider;

    private String senderId;
    private String senderName;
    private String senderHouseNumber;
    private String senderPostalZone;
    private String senderCity;
    private String senderAddress;
}
