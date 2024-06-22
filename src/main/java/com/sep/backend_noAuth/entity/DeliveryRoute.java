package com.sep.backend_noAuth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Document(collection = "delivery-routes")
public class DeliveryRoute {
    String route_id;
    String address_ids;
    String postman_id;
    Date date;


}
