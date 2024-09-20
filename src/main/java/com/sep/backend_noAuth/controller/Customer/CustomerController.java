package com.sep.backend_noAuth.controller.Customer;

import com.sep.backend_noAuth.dto.Customer.ProfileUpdateRequestDto;
import com.sep.backend_noAuth.entity.Customer;
import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.repository.AddressRepository;
import com.sep.backend_noAuth.repository.MailRepository;
import com.sep.backend_noAuth.service.AddressService;
import com.sep.backend_noAuth.service.CustomerService;
import com.sep.backend_noAuth.service.MailService;
//import com.sep.backend_noAuth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressService addressService;
    @Autowired
    private AddressRepository addressRepository;


    @GetMapping("/list/pending/{customerId}")
    public List<Mail> getAllPendingMails(@PathVariable String customerId){
        return mailService.getAllMailsForStatus(customerId,"pending");
    }

    @GetMapping("/list/delivered/{customerId}")
    public List<Mail> getAllDeliveredMails(@PathVariable String customerId) {
        return mailService.getAllMailsForStatus(customerId, "delivered");
    }
    @GetMapping("/list/profile/{customerId}")
    public Customer getProfileInfo(@PathVariable String customerId){
        return customerService.getCustomerInfo(customerId);
    }

    @PostMapping("/profile/update")
    public ResponseEntity<String> updateProfileInfo(@RequestBody ProfileUpdateRequestDto dto) {
        Optional<Customer> updatedCustomer = customerService.updateCustomer(dto);
        if(updatedCustomer.isPresent())
            return ResponseEntity.ok("Customer Update Success");
        return ResponseEntity.notFound().build();
    }

}
