package com.sep.backend_noAuth.controller.Receptionist;

import com.sep.backend_noAuth.dto.PostTypes.NormalPostDto;
import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.entity.MailTypes.NormalCourierPost;
import com.sep.backend_noAuth.entity.MailTypes.NormalPost;
import com.sep.backend_noAuth.repository.MailRepository;
import com.sep.backend_noAuth.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/receptionist/post/add/")
public class AddPostController {
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    private MailRepository mailRepository;
    @PostMapping("/normal-post")
    public ResponseEntity<String> addNormalPost(@RequestBody NormalPostDto normalPostDto){
        System.out.println(normalPostDto);
        return ResponseEntity.ok("Successfully Saved.");
    }
    @PostMapping("/test/create/normal-post")
    public ResponseEntity<String> createNormalPost(){
        Mail normalPost = new NormalPost();
        normalPost.setMailId(String.valueOf(sequenceGeneratorService.getSequenceNumber(Mail.SEQUENCE_NAME)));
        normalPost.setStatus("pending");
        normalPost.setCustomerId("2");
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
        ((NormalPost) normalPost).setTestField("Some Value");
        mailRepository.save(normalPost);
        return ResponseEntity.ok("Test Normal Mail Successfully Saved.");
    }
    @PostMapping("/test/create/normal-courier")
    public ResponseEntity<String> createNormalCourierPost(){
        Mail post = new NormalCourierPost();
        post.setMailId(String.valueOf(sequenceGeneratorService.getSequenceNumber(Mail.SEQUENCE_NAME)));
        post.setStatus("pending");
        post.setCustomerId("2");
        post.setDateDelivered(null);
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
}
