package com.sep.backend_noAuth.controller;

import com.sep.backend_noAuth.service.EmailService;
import com.sep.backend_noAuth.service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/mail")
public class EmailController {

    private EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public String sendMail(@RequestParam(value = "file", required = false)MultipartFile[] file,
                            String to,
                            String[] cc,
                            String subject,
                            String body){
        return emailService.sendMail(file, to, cc, subject, body);
    }

    @GetMapping("/test")
    public void testEmail(){
//        emailService.sendMail(null,"roshanem1772@gmail.com",null,"Return Mail","Your Mail Got Undelivered.");
    }
}
