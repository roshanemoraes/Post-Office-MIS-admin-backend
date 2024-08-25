package com.sep.backend_noAuth.controller.DeliveryManager;

import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.entity.UndeliverableMail;
import com.sep.backend_noAuth.repository.MailRepository;
import com.sep.backend_noAuth.repository.UndeliverableMailRepository;
import com.sep.backend_noAuth.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/delivery-manager/return-mail")
public class ReturnMailController {

    @Autowired
    private UndeliverableMailRepository undeliverableMailRepository;

    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

//    @GetMapping("list-all")
//    public List<Map<String, Object>> getReturnMailList() {
//        List<Map<String, Object>> returnMailList = Arrays.asList(
//                Map.of(
//                        "id", "137",
//                        "type", "Normal Post",
//                        "zone", "Pallansena South",
//                        "city", "Kochchikade",
//                        "reason", "Invalid Recipient Name",
//                        "returnDate", "2024-04-15"
//                ),
//                Map.of(
//                        "id", "144",
//                        "type", "Personal Courier",
//                        "zone", "Katana",
//                        "city", "Kochchikade",
//                        "reason", "Damaged",
//                        "returnDate", "2024-04-25"
//                ),
//                Map.of(
//                        "id", "91",
//                        "type", "Personal Parcel",
//                        "zone", "Kattuwa",
//                        "city", "Negombo",
//                        "reason", "Over weight",
//                        "returnDate", "2024-03-15"
//                ),
//                Map.of(
//                        "id", "111",
//                        "type", "Registered Post",
//                        "zone", "Pallansena North",
//                        "city", "Kochchikade",
//                        "reason", "Invalid Address",
//                        "returnDate", "2024-01-15"
//                )
//        );
//        return returnMailList;
//    }
//    @GetMapping("/list-all")
//    public Optional<List<UndeliverableMail>> getAllMails(){
//        List<UndeliverableMail> list = undeliverableMailRepository.findAll();
//        return Optional.of(list);
//    }
    @GetMapping("/list-all")
    public List<UndeliverableMail> getAllUndeliveredMails(){
        List<UndeliverableMail> list = undeliverableMailRepository.findByStatus("Undelivered");
        return list;
    }

    @GetMapping("/return-to-sender")
    public List<UndeliverableMail> getReturnToSenderUndeliveredMails(){
        List<UndeliverableMail> list = undeliverableMailRepository.findByStatus("Return-to-Sender");
        return list;
    }
    @PostMapping("/return-to-sender/add/{undeliverableId}")
    public ResponseEntity<String> doForOneMail(@PathVariable String undeliverableId){
        UndeliverableMail undeliverableMail = undeliverableMailRepository.findByUndeliverableId(undeliverableId);
        Mail mail = mailRepository.findByMailId(undeliverableMail.getMailId());

        if (mail!=null){
            Mail newMail = new Mail();
            newMail.setMailId(String.valueOf(sequenceGeneratorService.getSequenceNumber(Mail.SEQUENCE_NAME)));
            newMail.setStatus("Pending");
            newMail.setCustomerId("PO-Return");
            newMail.setRecipientId(undeliverableMail.getCustomer_id());
            newMail.setMailType(undeliverableMail.getType());
            newMail.setDateDelivered("");
            newMail.setDatePosted(String.valueOf(new Date()));
            newMail.setRecipientName(mail.getRecipientName());
            newMail.setDestinationAddress(mail.getDestinationAddress());
            mailRepository.save(newMail);
            undeliverableMail.setStatus("Return-to-Sender-Done");
            undeliverableMailRepository.save(undeliverableMail);
            return ResponseEntity.ok("Add to Mail Table Success.");
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/address-update")
    public List<UndeliverableMail> getAddressUpdateUndeliveredMails(){
        List<UndeliverableMail> list = undeliverableMailRepository.findByStatus("Address-Update-Pending");
        return list;
    }
    @GetMapping("/discarded-mail")
    public List<UndeliverableMail> getDiscardedMails(){
        List<UndeliverableMail> list = undeliverableMailRepository.findByStatus("Discarded");
        return list;
    }
    @PostMapping("/add/discarded-mail")
    public ResponseEntity<String> addToDiscardedMails(@RequestBody String undeliverableId){
        UndeliverableMail undeliverableMail = undeliverableMailRepository.findByUndeliverableId(undeliverableId);
        if(undeliverableMail != null){
            undeliverableMail.setStatus("Discarded");
            undeliverableMailRepository.save(undeliverableMail);
            return ResponseEntity.ok("Successfully Updated.");
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/add/return-to-sender")
    public ResponseEntity<String> addToReturnToSender(@RequestBody String undeliverableId){
        UndeliverableMail undeliverableMail = undeliverableMailRepository.findByUndeliverableId(undeliverableId);
        if(undeliverableMail != null){
            undeliverableMail.setStatus("Return-to-Sender");
            undeliverableMailRepository.save(undeliverableMail);
            return ResponseEntity.ok("Successfully Updated.");
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/address-update/add/{undeliverableId}")
    public ResponseEntity<String> addToAddressUpdate(@PathVariable String undeliverableId){
        UndeliverableMail undeliverableMail = undeliverableMailRepository.findByUndeliverableId(undeliverableId);
        if(undeliverableMail != null){
            undeliverableMail.setStatus("Address-Update-Pending");
            undeliverableMailRepository.save(undeliverableMail);
            return ResponseEntity.ok("Successfully Updated.");
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/add")
    public UndeliverableMail testAdd(){
        UndeliverableMail undeliverableMail = new UndeliverableMail(
                "6",
                "137",
                "6",
                "Undelivered",
                "Normal Post",
                "Pallansena South",
                "Kochchikade",
                "Invalid Recipient Name",
                "Emp003",
                "2024-04-15");
        undeliverableMailRepository.save(undeliverableMail);
        return undeliverableMail;
    }

    @GetMapping("/get-undeliverable-mail/{mailId}")
    public Mail getUndeliverableMail(@PathVariable String mailId){
        return mailRepository.findByMailId(mailId);
    }
}
