package com.sep.backend_noAuth.repository;

import com.sep.backend_noAuth.entity.Mail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MailRepository extends MongoRepository<Mail,String> {
    List<Mail> findByCustomerId(String customerId);
    Mail findByMailId(String mailId);
    List<Mail> findByDatePostedAndMailType(String datePosted, String mailType);
//    List<Mail>findByMailTypeAND(String mailType);
    @Query("{ 'datePosted': { $regex: '^?0' }, 'mailType': ?1 }")
    List<Mail> findByDatePrefixAndMailType(String datePrefix, String mailType);

}
