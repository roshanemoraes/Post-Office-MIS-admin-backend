package com.sep.backend_noAuth.controller.Receptionist;

import com.sep.backend_noAuth.dto.Receptionist.CustomerInfoDto;
import com.sep.backend_noAuth.entity.Customer;
import com.sep.backend_noAuth.repository.AddressRepository;
import com.sep.backend_noAuth.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/receptionist/customer-detail")
public class CustomerInfoController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("/")
    public ResponseEntity<CustomerInfoDto> getCustomerInfo(@RequestParam String searchTerm, @RequestParam String searchType) {
        searchTerm = searchTerm.trim();
        Optional<Customer> customer = Optional.empty();
        CustomerInfoDto dto = new CustomerInfoDto();

        if (searchType.equals("id")) {

            try {
                String sanitizedSearchTerm = searchTerm.replaceAll("[^\\d]", "");
                customer = customerRepository.findById(String.valueOf(sanitizedSearchTerm));

            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid ID format");
            }
        } else if (searchType.equals("nic")) {
            try {
                String sanitizedSearchTerm = searchTerm.replaceAll("[^\\d]", "");
                customer = customerRepository.findByNic(String.valueOf(sanitizedSearchTerm));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid nic format");
            }
        } else if (searchType.equals("contactNumber")) {
            try {
                String sanitizedSearchTerm = searchTerm.replaceAll("[^\\d]", "");
                customer = customerRepository.findByContactNumber(sanitizedSearchTerm);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid contact number format");
            }
        }
        if(customer.isPresent()){
            dto.setCustomer(customer.get());
            dto.setAddress(addressRepository.findByAddressId(customer.get().getAddressId()).getTextForm());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }
}
