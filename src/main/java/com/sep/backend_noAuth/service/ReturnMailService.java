package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.dto.AddressUpdateRequestDto;
import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.entity.UndeliverableMail;
import com.sep.backend_noAuth.repository.MailRepository;
import com.sep.backend_noAuth.repository.UndeliverableMailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReturnMailService {

    @Autowired
    private UndeliverableMailRepository undeliverableMailRepository;

    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

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

        Mail mail = mailRepository.findByMailId(dto.getMailId());
        mail.setStatus("pending");
        mailRepository.save(mail);
    }
}

