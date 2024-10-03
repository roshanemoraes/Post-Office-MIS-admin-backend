package com.sep.backend_noAuth.repository;

import com.sep.backend_noAuth.entity.BulkMailOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BulkMailOrderRepository extends MongoRepository<BulkMailOrder,String> {
}
