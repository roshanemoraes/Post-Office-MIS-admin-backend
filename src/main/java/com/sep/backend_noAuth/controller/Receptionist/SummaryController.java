package com.sep.backend_noAuth.controller.Receptionist;

import com.sep.backend_noAuth.dto.SummaryMailDto;
import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/receptionist/summary")
public class SummaryController {

    @Autowired
    private MailRepository mailRepository;

    @GetMapping("/get-mails")
    public List<Mail> getMailsForSummary(@RequestParam String mailType, @RequestParam String datePosted) {
        System.out.println(mailType);
        System.out.println(datePosted);
        return mailRepository.findByDatePostedAndMailType(datePosted, mailType);
    }

}
