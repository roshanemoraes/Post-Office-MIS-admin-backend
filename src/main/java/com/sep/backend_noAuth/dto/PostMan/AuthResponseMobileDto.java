package com.sep.backend_noAuth.dto.PostMan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponseMobileDto {
    private String username;
    private String postmanId;
    private String email;
    private String role;
    private String message;
    private String token;
}
