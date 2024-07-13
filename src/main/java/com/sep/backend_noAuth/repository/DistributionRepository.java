package com.sep.backend_noAuth.repository;

import com.sep.backend_noAuth.entity.Distribution;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DistributionRepository extends MongoRepository<Distribution, String> {
}
