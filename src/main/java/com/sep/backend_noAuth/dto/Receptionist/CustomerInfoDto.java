package com.sep.backend_noAuth.dto.Receptionist;

import com.sep.backend_noAuth.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerInfoDto {
    private Customer customer;
    private String address;

}
