package com.sep.backend_noAuth.entity;
import java.util.Date;

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

@Document(collection = "employee")
public class User {
    @Id
    private String id;
    private String userId;
    private String userName;
    private String fullName;
    private String email;
    private String nic;
    private String zone;
    private String contactNumber;
    private Date dateJoined;
    private String password;
    private String roles;
}
