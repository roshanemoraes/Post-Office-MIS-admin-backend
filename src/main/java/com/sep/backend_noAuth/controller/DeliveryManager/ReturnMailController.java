package com.sep.backend_noAuth.controller.DeliveryManager;

import com.sep.backend_noAuth.dto.AddressUpdateRequestDto;
import com.sep.backend_noAuth.entity.Customer;
import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.entity.MailTypes.NormalPost;
import com.sep.backend_noAuth.entity.UndeliverableMail;
import com.sep.backend_noAuth.repository.CustomerRepository;
import com.sep.backend_noAuth.repository.MailRepository;
import com.sep.backend_noAuth.repository.UndeliverableMailRepository;
import com.sep.backend_noAuth.service.EmailService;
import com.sep.backend_noAuth.service.ReturnMailService;
import com.sep.backend_noAuth.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/delivery-manager/return-mail")
public class ReturnMailController {

    @Autowired
    private UndeliverableMailRepository undeliverableMailRepository;

    @Autowired
    private ReturnMailService returnMailService;

    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/")
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

        if(undeliverableMail != null){
            Optional<Customer> customer = customerRepository.findById(undeliverableMail.getCustomer_id());
            if(customer.isPresent()){
                //Sending the email notification to the sender...
                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
                String date = currentDate.format(formatter);

                String mailBody = returnMailService.buildReturnToSenderEmail(customer.get().getFullName(),undeliverableId,mail.getDestinationAddress(),mail.getRecipientName(), date);
                emailService.sendMail(new MultipartFile[0],"roshanem1772@gmail.com",new String[0],"Return To Sender",mailBody);

                //Adding it as a new pending mail
                if (mail!=null){
                    Mail newMail = new NormalPost();
                    newMail.setMailId(String.valueOf(sequenceGeneratorService.getSequenceNumber(Mail.SEQUENCE_NAME)));
                    newMail.setStatus("Pending");
                    newMail.setCustomerId("PO-Return");
                    newMail.setPostage(0.0);
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
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/address-update")
    public List<UndeliverableMail> getAddressUpdateUndeliveredMails(){
//        List<UndeliverableMail> list = undeliverableMailRepository.findByStatus("Address-Update-Pending");
        List<UndeliverableMail> list = returnMailService.getNewAndPendingAddressUpdateMails();
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
    @PostMapping("/add/address-update")
    public ResponseEntity<String> addToAddressUpdate(@RequestBody String undeliverableId){
        UndeliverableMail undeliverableMail = undeliverableMailRepository.findByUndeliverableId(undeliverableId);
        if(undeliverableMail != null){
            undeliverableMail.setStatus("Address-Update");
            undeliverableMailRepository.save(undeliverableMail);
            return ResponseEntity.ok("Successfully Updated.");
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/address-update/add/{undeliverableId}")
    public ResponseEntity<String> processAddToAddressUpdate(@PathVariable String undeliverableId){
        UndeliverableMail undeliverableMail = undeliverableMailRepository.findByUndeliverableId(undeliverableId);
        if(undeliverableMail != null){
            undeliverableMail.setStatus("Address-Update-Pending");
            Optional<Customer> customer = customerRepository.findById(undeliverableMail.getCustomer_id());
            if(customer.isPresent()){
                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
                String date = currentDate.format(formatter);

                Mail mail = mailRepository.findByMailId(undeliverableMail.getMailId());
                String mailBody = returnMailService.buildAddressUpdateEmail(customer.get().getFullName(),undeliverableId,mail.getDestinationAddress(),mail.getRecipientName(), date);

                emailService.sendMail(new MultipartFile[0],customer.get().getEmail(),new String[0],"Address Update Request",mailBody);
                undeliverableMailRepository.save(undeliverableMail);
                return ResponseEntity.ok("Successfully Updated.");
            }
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/address-update/update")
    public ResponseEntity<String> updateAddress(@RequestBody AddressUpdateRequestDto dto){
        try {
            returnMailService.processAddressUpdate(dto);
            return ResponseEntity.status(200).body("Address Updated.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating address: " + e.getMessage());
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
