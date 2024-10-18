package com.sep.backend_noAuth.controller.DeliveryManager;

import com.sep.backend_noAuth.dto.AssignDeliveryReqDto;
import com.sep.backend_noAuth.dto.AssignDeliveryResDto;
import com.sep.backend_noAuth.entity.Delivery;
import com.sep.backend_noAuth.entity.Distribution;
import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.entity.PostmanAssignment;
import com.sep.backend_noAuth.repository.DistributionRepository;
import com.sep.backend_noAuth.repository.PostmanAssignmentRepository;
import com.sep.backend_noAuth.service.MailService;
import com.sep.backend_noAuth.service.MailSortService;
import com.sep.backend_noAuth.service.PostmanAssignmentLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    @Autowired
    private DistributionRepository distributionRepository;

    @Autowired
    private PostmanAssignmentLogService postmanAssignmentLogService;

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
    public List<AssignDeliveryResDto> getAllPostmanAssignments(){
        mailSortService.createUsualAssignmentPlanLog();
        List<PostmanAssignment> assignments = postmanAssignmentRepository.findAll();
        int assignmentCount = assignments.size();
        List<AssignDeliveryResDto> assignDeliveryResDtoList = new ArrayList<>(assignmentCount);

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        String date = currentDate.format(formatter);
        for (int i=0; i<assignmentCount; i++){
            PostmanAssignment assignment = assignments.get(i);
            Delivery delivery = mailSortService.findDeliveryForDateAndPostmanId(date, assignment.getPostmanId());
            AssignDeliveryResDto dto = new AssignDeliveryResDto();

            dto.setPostmanId(assignment.getPostmanId());
            dto.setZone(assignment.getZone());
            dto.setId(assignment.getId());
            if(delivery==null){
                dto.setDeliveryId("NA");
                dto.setStatus("Not Assigned");
            }else{
                dto.setDeliveryId(delivery.getDeliveryId());
                dto.setStatus("Assigned");
            }
            assignDeliveryResDtoList.add(i,dto);
        }
        return assignDeliveryResDtoList;
    }

//    @GetMapping("/all-postman-assignments")
//    public List<PostmanAssignment> getAllPostmanAssignments(){
//        return postmanAssignmentRepository.findAll();
//    }
    @GetMapping("/all-distribution-assignments")
    public List<Distribution> getAllDistributionAssignments(){
        return distributionRepository.findAll();
    }

    @PostMapping("/assign/add")
    public ResponseEntity<String> createDeliveryRecord(@RequestBody AssignDeliveryReqDto assignDeliveryReqDto) throws Exception {
        mailSortService.createDeliveryObject(assignDeliveryReqDto.getZone(), assignDeliveryReqDto.getPostmanId());
        return ResponseEntity.ok("Delivery Record Add Success.");
    }

    @GetMapping("/get-assignment-plan/status")
    public ResponseEntity<String> checkAssignmentPlanStatus(){
        String statusType = postmanAssignmentLogService.getTodayAssignmentStatus();
        return ResponseEntity.ok(statusType);
    }
//    @PostMapping("/create/usualAssignment-plan")
//    public ResponseEntity<Boolean> createUsualAssignmentPlan(){
//        return mailSortService.createUsualAssignmentPlanLog() ? ResponseEntity.ok(true) : ResponseEntity.ok(false);
//    }


}
