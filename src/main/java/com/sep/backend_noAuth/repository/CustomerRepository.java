package com.sep.backend_noAuth.repository;

import com.sep.backend_noAuth.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Optional<Customer> findById(String id);
}
