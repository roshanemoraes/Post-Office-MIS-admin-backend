package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.dto.PostMan.MailDetailsDto;
import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class RouteDisplayService {
    @Autowired
    private MailRepository mailRepository;

    public MailDetailsDto getMailDetails (String mailId){
        Mail mail = mailRepository.findByMailId(mailId);
        MailDetailsDto dto = new MailDetailsDto();
        
//        dto.setMailId(mail.getMailId());
        dto.setStatus(mail.getStatus());
        dto.setDestinationAddress(mail.getDestinationAddress());
        dto.setMailType(mail.getMailType());
        dto.setRecipientName(mail.getRecipientName());
        dto.setZone(mail.getZone());
        dto.setCity(mail.getCity());


        return dto;
    }
}
