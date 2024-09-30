package com.sep.backend_noAuth.repository;

import com.sep.backend_noAuth.entity.PostmanAssignment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PostmanAssignmentRepository extends MongoRepository<PostmanAssignment,Integer> {
    public Optional<PostmanAssignment> findByPostmanId(Long postmanId);
}
