package com.sep.backend_noAuth.repository;

import com.sep.backend_noAuth.entity.PostmanAssignment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostmanAssignmentRepository extends MongoRepository<PostmanAssignment,Integer> {
    //PostmanAssignment findByPostmanId(Integer postmanId);
}
