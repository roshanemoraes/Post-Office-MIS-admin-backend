package com.sep.backend_noAuth.dto.PostMan;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data //---this annotation gives getters and setters
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailDetailsDto {
//    private String mailId;

    private String status;
    private String  destinationAddress;
    private String mailType;
    private String recipientName;
    private String  zone;
    private String  city;
    private String datePosted;


}
