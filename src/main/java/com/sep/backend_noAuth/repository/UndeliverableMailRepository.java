package com.sep.backend_noAuth.repository;

import com.sep.backend_noAuth.entity.UndeliverableMail;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UndeliverableMailRepository extends MongoRepository<UndeliverableMail,String> {
    List<UndeliverableMail> findByStatus(String status);
    UndeliverableMail findByMailId(String mailId);
    UndeliverableMail findByUndeliverableId(String undeliverableID);
}
