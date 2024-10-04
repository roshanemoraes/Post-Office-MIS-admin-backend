package com.sep.backend_noAuth.controller.Customer;
import com.sep.backend_noAuth.dto.Customer.Financialdto;
import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mails")
public class FinancialController {

    @Autowired
    private MailRepository mailRepository;

    // Define the new endpoint

//    @GetMapping("/customer/financial")
//    public ResponseEntity<List<Mail>> getMailsByDatePrefixAndMailType(
//            @RequestParam("datePrefix") String datePrefix,
//            @RequestParam("mailType") String mailType) {
//
//        List<Mail> mails = mailRepository.findByDatePrefixAndMailType(datePrefix, mailType);
//
//
//
//        return ResponseEntity.ok(mails);
//    }
@GetMapping("/customer/financial")
public ResponseEntity<List<Financialdto>> getMailsByDatePrefixAndMailType(
        @RequestParam("datePrefix") String datePrefix,
        @RequestParam("mailType") String mailType) {

    // Fetching the mails from the repository
    List<Mail> mails = mailRepository.findByDatePrefixAndMailType(datePrefix, mailType);

    // Mapping Mail entities to Financialdto
    List<Financialdto> financialDtos = mails.stream().map(mail -> {
        Financialdto dto = new Financialdto();
//        dto.setMailId(mail.getMailId());
        dto.setId(mail.getMailId());
        dto.setDatePosted(mail.getDatePosted());

        dto.setDateDelivered(mail.getDateDelivered());
        dto.setCity(mail.getCity());
        dto.setPostage(mail.getPostage());
        return dto;
    }).collect(Collectors.toList());

    // Returning the list of Financialdto objects
    return ResponseEntity.ok(financialDtos);
}

}
