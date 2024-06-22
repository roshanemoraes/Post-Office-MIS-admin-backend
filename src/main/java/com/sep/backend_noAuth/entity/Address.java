package com.sep.backend_noAuth.entity;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Document(collection = "address" )
public class Address {
    String addressId;
    String textForm;
    String lat;
    String city;
    String zone;
    String houseNumber;
    String lng;
    List<String> members;
}
