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

@Document(collection = "user")
public class UserInfo {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String roles;
}
