package com.sep.backend_noAuth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AddressUpdateRequestDto {
    private String mailId;
    private String customerId;
    private String undeliverableId;
    private String newAddress;
    private String notificationId;
}
