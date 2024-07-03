package com.sep.backend_noAuth.entity;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Document(collection = "Address" )
public class Address {
    @Id
    String addressId;
    String textForm;
    String lat;
    String lng;
    String city;
    String zone;
    String houseNumber;
    List<String> members;
}
