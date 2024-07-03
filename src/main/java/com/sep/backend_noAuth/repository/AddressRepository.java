package com.sep.backend_noAuth.repository;

import com.sep.backend_noAuth.entity.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressRepository extends MongoRepository<Address,String> {
}
