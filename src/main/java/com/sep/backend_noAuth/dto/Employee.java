package com.sep.backend_noAuth.dto;

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

@Document(collection = "employee")
public class Employee {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String roles;
}
