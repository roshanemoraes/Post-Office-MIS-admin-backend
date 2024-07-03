package com.sep.backend_noAuth.entity;
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

@Document(collection = "Customer")
public class Customer {
    @Id
    String id;
    String userName;
    String fullName;
    String email;
    String nic;
    String addressId;
    String contactNumber;
    String dataJoined;
    String password;
    String roles;
}
