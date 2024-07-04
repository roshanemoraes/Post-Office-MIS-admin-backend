package com.sep.backend_noAuth.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AssignDeliveryReqDto {
    private String zone;
    private int postmanId;
}
