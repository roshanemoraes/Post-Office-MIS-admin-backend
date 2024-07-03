package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.entity.Mail;
import com.sep.backend_noAuth.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService {

    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Mail> getAllMailsForStatus(String customerId,String status){
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(customerId));
        query.addCriteria(Criteria.where("status").is(status));
        return mongoTemplate.find(query,Mail.class);
    }
}
