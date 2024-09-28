package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.entity.UserInfo;
import com.sep.backend_noAuth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<UserInfo> getListOfPostman(){
        Query query = new Query();
        query.addCriteria(Criteria.where("roles").is("Postman"));
        return mongoTemplate.find(query, UserInfo.class);
    }

}
