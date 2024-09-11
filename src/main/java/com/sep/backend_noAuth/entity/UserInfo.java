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

@Document(collection = "Employee")
public class UserInfo {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String roles;
    private String fullName;
    private String nic;
    private String contactNumber;
    private String dateJoined;
}
