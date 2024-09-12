package com.sep.backend_noAuth.controller.Postman;

import com.sep.backend_noAuth.dto.PostMan.MailStatusUpdateDto;
import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("api/postman/update-status")
public class MailStatusUpdateController {
    @Autowired
    private MailRepository mailRepository;
    // Endpoint to update mail status by mailId
    @PutMapping
    public ResponseEntity<String> updateMailStatus(@RequestBody MailStatusUpdateDto mailStatusUpdateDTO) {
        // Optional validation for status value (you can modify it based on your logic)
        if (!isValidStatus(mailStatusUpdateDTO.getStatus())) {
            return ResponseEntity.badRequest().body("Invalid status value");
        }
        // Find mail by mailId in MongoDB
        Optional<Mail> mailOptional = mailRepository.findById(mailStatusUpdateDTO.getMailId());

        if (mailOptional.isPresent()) {
            Mail mail = mailOptional.get();
            // Update the status
            mail.setStatus(mailStatusUpdateDTO.getStatus());
            // Save updated mail entity back to MongoDB
            mailRepository.save(mail);
            return ResponseEntity.ok("Mail status updated successfully");
        } else {
            return ResponseEntity.status(404).body("Mail not found with id: " + mailStatusUpdateDTO.getMailId());
        }
    }

    // Example of a simple status validation method
    private boolean isValidStatus(String status) {
        return status.equals("Delivered") || status.equals("Undelivered")|| status.equals("Undelivered - Other")|| status.equals("Undelivered - Wrong Address")|| status.equals("Undelivered - No Response");
    }
}
