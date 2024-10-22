package com.sep.backend_noAuth.repository;

import com.sep.backend_noAuth.entity.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends MongoRepository<Payment , Long> {
    List<Payment> findByMailType(String mailType);
    //List<Payment> findByMailTypeAndMonth(String mailType, int month);
}
