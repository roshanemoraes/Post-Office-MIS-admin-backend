package com.sep.backend_noAuth.controller.Postman;

import com.sep.backend_noAuth.dto.AddressMemberDto;
import com.sep.backend_noAuth.dto.PostMan.AddAddressDto;
import com.sep.backend_noAuth.entity.Address;
import com.sep.backend_noAuth.repository.AddressRepository;
import com.sep.backend_noAuth.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/postman/address")

public class AddAddressController {
    @Autowired
    private AddressRepository  addressRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @PostMapping("/add-address")
    public ResponseEntity<Address> addAddress(@RequestBody AddAddressDto addAddressDto){
        // Construct the full textForm based on the provided data
        Address address=new Address();
        address.setAddressId(String.valueOf(sequenceGeneratorService.getSequenceNumber(Address.SEQUENCE_NAME)));
        address.setCity(addAddressDto.getCity());
        address.setZone(addAddressDto.getZone());
        address.setHouseNumber(addAddressDto.getHouseNumber());

        if (addAddressDto.getLocation() != null) {
            address.setLat(addAddressDto.getLocation().getLatitude());
            address.setLng(addAddressDto.getLocation().getLongitude());
        }
        // Construct the textForm field
        String textForm = addAddressDto.getHouseNumber() + ", " + addAddressDto.getZone() + ", " + addAddressDto.getCity();
        address.setTextForm(textForm);

        // Map members from DTO to entity
        if (addAddressDto.getMembers() != null) {
            List<AddressMemberDto> members = addAddressDto.getMembers().stream()
                    .map(memberDto -> new AddressMemberDto("unreg", memberDto.getName()))
                    .collect(Collectors.toList());
            address.setMembers(members);  // Set the members
        }

// Save the address to MongoDB
        Address savedAddress = addressRepository.save(address);
        return ResponseEntity.ok(savedAddress);
    }

}
