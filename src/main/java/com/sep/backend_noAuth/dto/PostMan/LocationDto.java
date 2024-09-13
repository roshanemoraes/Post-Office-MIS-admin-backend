package com.sep.backend_noAuth.dto.PostMan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class LocationDto {
    private String latitude;
    private String longitude;
}
