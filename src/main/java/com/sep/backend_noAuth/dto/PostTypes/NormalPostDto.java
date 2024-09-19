package com.sep.backend_noAuth.dto.PostTypes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NormalPostDto {
    String recipientName;
    String recipientHouseNumber;
    String recipientPostalZone;
    String recipientCity;
    String recipientAddress;

    String senderName;
    String senderHouseNumber;
    String senderPostalZone;
    String senderCity;
    String senderAddress;
}