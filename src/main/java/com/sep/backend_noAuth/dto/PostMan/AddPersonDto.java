package com.sep.backend_noAuth.dto.PostMan;

import com.sep.backend_noAuth.dto.AddressMemberDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddPersonDto {
    private String addressId;
    private List<AddressMemberDto> members;
}
