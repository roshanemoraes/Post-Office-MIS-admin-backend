package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.entity.Customer;
import com.sep.backend_noAuth.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Customer getCustomerInfo(String customerId){
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(customerId));
        return mongoTemplate.findOne(query, Customer.class);
    }
}
