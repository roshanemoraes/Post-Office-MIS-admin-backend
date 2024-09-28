package com.sep.backend_noAuth.repository;

import com.sep.backend_noAuth.entity.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PaymentRepository extends MongoRepository<Payment , String> {
    List<Payment> findByMailType(String mailType);
    List<Payment> findByDatePostedAndMailType(String datePosted, String mailType);
}
