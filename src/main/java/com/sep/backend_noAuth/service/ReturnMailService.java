package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.dto.AddressUpdateRequestDto;
import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.entity.Notification;
import com.sep.backend_noAuth.entity.UndeliverableMail;
import com.sep.backend_noAuth.repository.MailRepository;
import com.sep.backend_noAuth.repository.NotificationRepository;
import com.sep.backend_noAuth.repository.UndeliverableMailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReturnMailService {

    @Autowired
    private UndeliverableMailRepository undeliverableMailRepository;

    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private AddressService addressService;

    public List<UndeliverableMail> getNewAndPendingAddressUpdateMails() {
        Query query = new Query();
        query.addCriteria(
                new Criteria().orOperator(
                        Criteria.where("status").is("Address-Update"),
                        Criteria.where("status").is("Address-Update-Pending"),
                        Criteria.where("status").is("Address-Update-Done")
                )
        );
        return mongoTemplate.find(query, UndeliverableMail.class);
    }

    public void processAddressUpdate(AddressUpdateRequestDto dto){
        UndeliverableMail returnMail = undeliverableMailRepository.findByUndeliverableId(dto.getUndeliverableId());
        returnMail.setStatus("Address-Update-Done");
        undeliverableMailRepository.save(returnMail);
        Mail oldMail = mailRepository.findByMailId(dto.getMailId());
        addressService.updateCustomerAddress(oldMail.getAddressId(),dto.getCustomerId());
        Optional<Notification> notification = notificationRepository.findByNotificationId(dto.getNotificationId());
        if(notification.isPresent()){
            notification.get().setType("Address-update-complete");
            notificationRepository.save(notification.get());
        }
        Mail mail = mailRepository.findByMailId(dto.getMailId());
        mail.setStatus("pending");
        mailRepository.save(mail);
    }
    public String buildAddressUpdateEmail(String customerName, String undeliveredMailId, String recipientAddress, String recipientName, String date) {
        return "<html>" +
                "<body>" +
                "<p>Dear " + customerName + ",</p>" +
                "<p>We hope this message finds you well.</p>" +
                "<p>We are writing to inform you that one of your mailed items has been returned to the post office due to an undeliverable address. To ensure the successful delivery of your mail, we kindly request you to update your address information.</p>" +
                "<p><strong>Details of the mail:</strong></p>" +
                "<ul>" +
                "<li><strong>Return Mail ID:</strong> " + undeliveredMailId + "</li>" +
                "<li><strong>Current Recipient Address:</strong> " + recipientAddress + "</li>" +
                "<li><strong>Current Recipient Name:</strong> " + recipientName + "</li>" +
                "<li><strong>Date:</strong> " + date + "</li>" +
                "<li><strong>Current Status:</strong> Address Update Required</li>" +
                "</ul>" +
                "<p>Please log into your account and update your address as soon as possible to avoid any further delays in delivering your mail.</p>" +
                "<p>If you have any questions, feel free to contact us at +94 716 123432.</p>" +
                "<p>Thank you for your attention to this matter.</p>" +
                "<p>Best regards,</p>" +
                "<p>Negombo Post Office</p>" +
                "</body>" +
                "</html>";
    }
    public String buildReturnToSenderEmail(String customerName, String undeliveredMailId, String recipientAddress, String recipientName, String date) {
        return "<html>" +
                "<body>" +
                "<p>Dear " + customerName + ",</p>" +
                "<p>We hope this message finds you well.</p>" +
                "<p>We are writing to inform you that your mailed item has been returned to the post office due to an issue with the mail item. As per our policy, the item will be returned to you.</p>" +
                "<p><strong>Details of the mail:</strong></p>" +
                "<ul>" +
                "<li><strong>Return Mail ID:</strong> " + undeliveredMailId + "</li>" +
                "<li><strong>Recipient Address:</strong> " + recipientAddress + "</li>" +
                "<li><strong>Recipient Name:</strong> " + recipientName + "</li>" +
                "<li><strong>Date:</strong> " + date + "</li>" +
                "<li><strong>Current Status:</strong> Return To Sender Required</li>" +
                "</ul>" +
                "<p>If you have any questions, feel free to contact us at +94 716 123432.</p>" +
                "<p>Thank you for your attention to this matter.</p>" +
                "<p>Best regards,</p>" +
                "<p>Negombo Post Office</p>" +
                "</body>" +
                "</html>";
    }

}

