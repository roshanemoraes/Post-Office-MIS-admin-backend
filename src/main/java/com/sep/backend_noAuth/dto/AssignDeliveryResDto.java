package com.sep.backend_noAuth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AssignDeliveryResDto {
    private Integer id;
    private String deliveryId;
    private Long postmanId;
    private String zone;
    private String status;
}
