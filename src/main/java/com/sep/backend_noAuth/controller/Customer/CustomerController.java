package com.sep.backend_noAuth.controller.Customer;

import com.sep.backend_noAuth.entity.Customer;
import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.repository.MailRepository;
import com.sep.backend_noAuth.service.CustomerService;
import com.sep.backend_noAuth.service.MailService;
import com.sep.backend_noAuth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        Customer profile = customerService.getCustomerInfo(customerId);
        return profile;
    }


}
