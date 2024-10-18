package com.sep.backend_noAuth.dto.Customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Financialdto {
    private String id;
    private String datePosted;
    private String dateDelivered;
    private String city;
    private double postage;


}
