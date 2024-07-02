package com.sep.backend_noAuth.repository;

import com.sep.backend_noAuth.entity.Delivery;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeliveryRepository extends MongoRepository<Delivery,String> {
}
