package com.sep.backend_noAuth.entity;

import com.sep.backend_noAuth.dto.DestinationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Document(collection = "Delivery")
public class Delivery {
    @Id
    private String deliveryId;
    private String postmanId;
    private String date;   //TODO: here, when using LocalDateTime as the datatype, request is taking more time since it need to do mapping. So, just used String.
    private List<DestinationDto> destinations;
    private String visitOrder;
    private String zone;
    private String status;
    private int deliveredCount;
}
