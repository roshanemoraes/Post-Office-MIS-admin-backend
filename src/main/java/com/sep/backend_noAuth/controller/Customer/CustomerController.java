package com.sep.backend_noAuth.controller.Customer;

import com.sep.backend_noAuth.dto.Customer.ProfileUpdateRequestDto;
import com.sep.backend_noAuth.dto.PostTypes.NormalPost;
import com.sep.backend_noAuth.entity.Customer;
import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.repository.MailRepository;
import com.sep.backend_noAuth.service.CustomerService;
import com.sep.backend_noAuth.service.MailService;
import com.sep.backend_noAuth.service.UserService;
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


    @GetMapping("/list/pending/{customerId}")
    public List<Mail> getAllPendingMails(@PathVariable String customerId){
        List<Mail> list = mailService.getAllMailsForStatus(customerId,"pending");
        return list;
    }

    @GetMapping("/list/delivered/{customerId}")
    public List<Mail> getAllDeliveredMails(@PathVariable String customerId){
        List<Mail> list = mailService.getAllMailsForStatus(customerId,"delivered");
        return list;
    }

    @GetMapping("/list/profile/{customerId}")
    public Customer getProfileInfo(@PathVariable String customerId){
        Customer customerInfo = customerService.getCustomerInfo(customerId);
        return customerInfo;
    }

    @PostMapping("/profile/update")
    public ResponseEntity<String> updateProfileInfo(@RequestBody ProfileUpdateRequestDto dto) {
        Optional<Customer> newCustomer = customerService.updateCustomer(dto);
        if (newCustomer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Profile Updated.");
    }
}
