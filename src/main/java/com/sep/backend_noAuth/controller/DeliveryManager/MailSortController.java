package com.sep.backend_noAuth.controller.DeliveryManager;

import com.sep.backend_noAuth.dto.AssignDeliveryDto;
import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.entity.PostmanAssignment;
import com.sep.backend_noAuth.repository.PostmanAssignmentRepository;
import com.sep.backend_noAuth.service.MailService;
import com.sep.backend_noAuth.service.MailSortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery-manager/sort")
public class MailSortController {

    @Autowired
    private MailService mailService;

    @Autowired
    private PostmanAssignmentRepository postmanAssignmentRepository;

    @Autowired
    private MailSortService mailSortService;

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

    @PostMapping("/assign/add")
    public ResponseEntity<String> createDeliveryRecord(@RequestBody AssignDeliveryDto assignDeliveryDto){
        mailSortService.createDeliveryObject(assignDeliveryDto.getZone(), assignDeliveryDto.getPostmanId());
        return ResponseEntity.ok("Delivery Record Add Success.");
    }


}
