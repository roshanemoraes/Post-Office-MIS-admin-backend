package com.sep.backend_noAuth.controller.DeliveryManager;

import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.entity.PostmanAssignment;
import com.sep.backend_noAuth.repository.PostmanAssignmentRepository;
import com.sep.backend_noAuth.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/delivery-manager/sort")
public class MailSort {

    @Autowired
    private MailService mailService;

    @Autowired
    private PostmanAssignmentRepository postmanAssignmentRepository;

    @GetMapping("/all-pending")
    public List<Mail> getALLPendingMails(){
        return mailService.getAllPendingMails();
    }
    @GetMapping("/all-pending-in-area")
    public List<Mail> getALLPendingInAreaMails(){
        return mailService.getAllInAreaMails();
    }
    @GetMapping("/all-pending-out-area")
    public List<Mail> getALLPendingOutAreaMails(){
        return mailService.getAllOutAreaMails();
    }
    @GetMapping("/all-postman-assignments")
    public List<PostmanAssignment> getAllPostmanAssignments(){
        return postmanAssignmentRepository.findAll();
    }


}
