package com.sep.backend_noAuth.repository;

import com.sep.backend_noAuth.entity.PostmanAssignmentLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PostmanAssignmentLogRepository extends MongoRepository<PostmanAssignmentLog,String> {
    Optional<PostmanAssignmentLog> findByDate(String date);
}
