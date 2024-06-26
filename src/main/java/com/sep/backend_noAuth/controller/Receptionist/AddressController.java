package com.sep.backend_noAuth.controller.Receptionist;

import com.sep.backend_noAuth.dto.AddressRequest;
import com.sep.backend_noAuth.entity.Address;
import com.sep.backend_noAuth.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/receptionist/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/validateDummy")
    public ResponseEntity<Address> getValidatedAddress_dummy(){
        Address address = addressService.getAddressToValidateDummy();
        if(address == null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(address);
        }
    }
    @PostMapping("/validate")
    public ResponseEntity<Address> getValidatedAddress(@RequestBody AddressRequest addressRequest){
        Address address = addressService.getAddressToValidate(addressRequest);
        if(address == null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(address);
        }
    }
}
