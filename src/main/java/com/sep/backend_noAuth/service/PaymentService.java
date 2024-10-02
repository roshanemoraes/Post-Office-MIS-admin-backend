package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.entity.Payment;
import com.sep.backend_noAuth.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Payment> getPaymentDetailsByMailType(String mailType){
       Query query = new Query();
       query.addCriteria(Criteria.where("mailType").is(mailType));
       return mongoTemplate.find(query, Payment.class);
    }



}
