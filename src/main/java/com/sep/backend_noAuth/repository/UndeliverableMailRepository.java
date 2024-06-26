package com.sep.backend_noAuth.repository;

import com.sep.backend_noAuth.entity.DeliveryManager.UndeliverableMail;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UndeliverableMailRepository extends MongoRepository<UndeliverableMail,String> {
}
