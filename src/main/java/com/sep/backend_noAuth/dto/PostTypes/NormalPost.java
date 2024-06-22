package com.sep.backend_noAuth.dto.PostTypes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NormalPost {
    String recipientHouseNumber;
    String recipientName;
    

}
