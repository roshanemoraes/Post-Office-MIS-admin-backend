package com.sep.backend_noAuth.repository;

import com.sep.backend_noAuth.entity.Mail;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MailRepository extends MongoRepository<Mail,String> {
    List<Mail> findByCustomerId(String customerId);
    Mail findByMailId(String mailId);
}
