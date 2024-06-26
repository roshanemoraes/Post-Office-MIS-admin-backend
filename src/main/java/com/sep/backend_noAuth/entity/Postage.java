package com.sep.backend_noAuth.entity;

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

@Document(collection = "postage-normal-posts")
public class Postage {
    @Id
    String Id;
    int minWeight;
    int maxWeight;
    double price;
}
