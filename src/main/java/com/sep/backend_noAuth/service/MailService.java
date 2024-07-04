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
        query.addCriteria(Criteria.where("customerId").is(customerId));
        query.addCriteria(Criteria.where("status").is(status));
        return mongoTemplate.find(query,Mail.class);
    }
    public List<Mail> getAllPendingMails(){
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is("pending"));
        return mongoTemplate.find(query,Mail.class);
    }

    public List<Mail> getAllInAreaMails(){
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is("pending"));
        query.addCriteria(Criteria.where("in_area").is(true));
        return mongoTemplate.find(query,Mail.class);
    }
    public List<Mail> getAllOutAreaMails(){
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is("pending"));
        query.addCriteria(Criteria.where("in_area").is(false));
        return mongoTemplate.find(query,Mail.class);
    }
    public List<Mail> getAllPendingInAreaMailsForZone(String zone){
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is("pending"));
        query.addCriteria(Criteria.where("in_area").is(true));
        query.addCriteria(Criteria.where("zone").is(zone));
        return mongoTemplate.find(query,Mail.class);
    }
}
