package com.sep.backend_noAuth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Document(collection = "postage-normal-post")
public class Postage {
    @Id
    String Id;
    int minWeight;
    int maxWeight;
    double price;
}
