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
@Document(collection = "Bulk-Mail-Orders")
public class BulkMailOrder {
    @Id
    private String orderId;
    private String postalCost;
    private String totalWeight;
    private String status;
    private int customerId;
    private int itemCount;
    private String orderDate;
    private List<String> mailList;
}
