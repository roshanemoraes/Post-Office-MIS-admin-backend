package com.sep.backend_noAuth.controller.Receptionist;

import com.sep.backend_noAuth.dto.PostTypes.GovParcelPostDto;
import com.sep.backend_noAuth.dto.PostTypes.NormalCourierPostDto;
import com.sep.backend_noAuth.dto.PostTypes.NormalParcelPostDto;
import com.sep.backend_noAuth.dto.PostTypes.NormalPostDto;
import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.entity.MailTypes.NormalCourierPost;
import com.sep.backend_noAuth.entity.MailTypes.NormalPost;
import com.sep.backend_noAuth.repository.MailRepository;
import com.sep.backend_noAuth.service.MailRegistrationService;
import com.sep.backend_noAuth.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/receptionist/post/add")
public class AddPostController {
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    private MailRepository mailRepository;
    @Autowired
    private MailRegistrationService mailRegistrationService;
    @PostMapping("/normal-post")
    public ResponseEntity<String> addNormalPost(@RequestBody NormalPostDto normalPostDto){
        return mailRegistrationService.registerNormalPost(normalPostDto);
    }
    @PostMapping("/normal-courier")
    public ResponseEntity<String> addNormalCourierPost(@RequestBody NormalCourierPostDto normalCourierPostDto){
        return mailRegistrationService.registerNormalCourierPost(normalCourierPostDto);
    }
    @PostMapping("/normal-parcel")
    public ResponseEntity<String> addNormalParcelPost(@RequestBody NormalParcelPostDto normalParcelPostDto){
        return mailRegistrationService.registerNormalParcelPost(normalParcelPostDto);
    }
    @PostMapping("/gov-parcel")
    public ResponseEntity<String> addGovParcelPost(@RequestBody GovParcelPostDto govParcelPostDto){
        return mailRegistrationService.registerGovParcelPost(govParcelPostDto);
    }

    @PostMapping("/test/create/normal-post")
    public ResponseEntity<String> createTestNormalPost(){
        Mail normalPost = new NormalPost();
        normalPost.setMailId(String.valueOf(sequenceGeneratorService.getSequenceNumber(Mail.SEQUENCE_NAME)));
        normalPost.setStatus("pending");
        normalPost.setCustomerId("2");
        normalPost.setPostage(0.0);
        normalPost.setDateDelivered(null);
        normalPost.setDatePosted("2024-06-30");
        normalPost.setDestinationAddress("1,Pallansena South,Kochchikade");
        normalPost.setRecipientName("K.D.Kamal Perera");
        normalPost.setZone("Pallansena South");
        normalPost.setCity("Kochchikade");
        normalPost.setAddressId("1");
        normalPost.setIn_area(true);
        normalPost.setRecipientId("3");
        normalPost.setRecipientName(null);
        ((NormalPost) normalPost).setSenderType("registered");
        mailRepository.save(normalPost);
        return ResponseEntity.ok("Test Normal Mail Successfully Saved.");
    }
    @PostMapping("/test/create/normal-courier")
    public ResponseEntity<String> createTestNormalCourierPost(){
        Mail post = new NormalCourierPost();
        post.setMailId(String.valueOf(sequenceGeneratorService.getSequenceNumber(Mail.SEQUENCE_NAME)));
        post.setStatus("pending");
        post.setCustomerId("2");
        post.setDateDelivered(null);
        post.setPostage(0.0);
        post.setDatePosted("2024-06-30");
        post.setDestinationAddress("1,Pallansena South,Kochchikade");
        post.setRecipientName("K.D.Kamal Perera");
        post.setZone("Pallansena South");
        post.setCity("Kochchikade");
        post.setAddressId("1");
        post.setIn_area(true);
        post.setRecipientId("3");
        post.setRecipientName(null);
        ((NormalCourierPost) post).setCourierService("FedEx");
        mailRepository.save(post);
        return ResponseEntity.ok("Test Normal Courier Mail Successfully Saved.");
    }
    @GetMapping("/test/get")
    public ResponseEntity<List<Mail>> getTestingMail() {
        List<Mail> mails = new ArrayList<>();
        Mail mail1 = mailRepository.findByMailId("26");
        Mail mail2 = mailRepository.findByMailId("1");
        if (mail1 != null) {
            mails.add(mail1);
        }

        if (mail2 != null) {
            mails.add(mail2);
        }
        return ResponseEntity.ok(mails);
    }
    @DeleteMapping("/delete/mail/{mailId}")
    public ResponseEntity<String> deleteMailById(@PathVariable String mailId) {
        Optional<Mail> mail = Optional.ofNullable(mailRepository.findByMailId(mailId));
        if (mail.isPresent()) {
            mailRepository.delete(mail.get());
            return ResponseEntity.ok("Mail item deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
