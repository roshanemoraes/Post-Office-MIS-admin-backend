package com.sep.backend_noAuth.repository;

import com.sep.backend_noAuth.entity.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AddressRepository extends MongoRepository<Address,String> {
    Address findByAddressId(String addressId);
    Optional<Address> findByHouseNumberAndZoneAndCity(String houseNumber, String zone, String city);
}
