package com.sep.backend_noAuth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Document(collection = "Distribution")
public class Distribution {

    @Id
    private String distributionId;
    private String city;
    private String date;
    private List<String> mail_list;
    private String status;
    private int vehicleId;

}
