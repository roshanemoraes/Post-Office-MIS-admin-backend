package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.entity.Postage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class PostageService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Postage findPostageByWeight(double weight){
        Query query = new Query();
        query.addCriteria(Criteria.where("minWeight").lte(weight));
        query.addCriteria(Criteria.where("maxWeight").gte(weight));
        return mongoTemplate.findOne(query, Postage.class);
    }
}
