package com.sep.backend_noAuth.dto.Customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProfileUpdateRequestDto {
    String id;
    String address;
    String contact;
}