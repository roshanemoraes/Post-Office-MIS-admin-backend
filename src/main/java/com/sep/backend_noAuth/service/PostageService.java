package com.sep.backend_noAuth.service;

import com.sep.backend_noAuth.entity.Postage.PostageCourierNormal;
import com.sep.backend_noAuth.entity.Postage.PostageNormalPosts;
import com.sep.backend_noAuth.entity.Postage.PostageParcelGov;
import com.sep.backend_noAuth.entity.Postage.PostageParcelNormal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostageService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public PostageNormalPosts findPostageByWeight(double weight){
        Query query = new Query();
        query.addCriteria(Criteria.where("minWeight").lte(weight));
        query.addCriteria(Criteria.where("maxWeight").gte(weight));
        return mongoTemplate.findOne(query, PostageNormalPosts.class);
    }
    public List<PostageNormalPosts> getNormalPostageList() {
        return mongoTemplate.findAll(PostageNormalPosts.class);
    }
    public List<PostageCourierNormal> getCourierNormalPostageList(){
        return mongoTemplate.findAll(PostageCourierNormal.class);
    }
    public List<PostageParcelGov> getParcelGovPostageList(){
        return mongoTemplate.findAll(PostageParcelGov.class);
    }
    public List<PostageParcelNormal> getParcelNormalPostageList(){
        return mongoTemplate.findAll(PostageParcelNormal.class);
    }


}
