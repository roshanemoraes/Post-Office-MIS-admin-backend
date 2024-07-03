package com.sep.backend_noAuth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AddEmployeeDto {
    private String role;
    private String employeeFullName;
    private String employeeNIC;
    private String employeeUserName;
    private String employeeEmail;
    private String employeeContactNumber;
    private String employeeDateJoined;
    private String accountPassword;
}
